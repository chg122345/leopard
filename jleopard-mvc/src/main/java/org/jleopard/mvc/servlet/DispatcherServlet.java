/**
 * @author (c) 2018, Chen_9g 陈刚 (80588183@qq.com).
 * @date 2018-10-03  下午8:00
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */

package org.jleopard.mvc.servlet;

import org.jleopard.mvc.DefaultAppContext;
import org.jleopard.mvc.core.AppContext;
import org.jleopard.mvc.core.annotation.*;
import org.jleopard.mvc.core.bean.MappingInfo;
import org.jleopard.mvc.view.View;
import org.jleopard.util.ClassUtil;
import org.jleopard.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 转发控制器
 */
public class DispatcherServlet extends HttpServlet {

    private AppContext appContext;

    private final static String VIEW_RESOLVER_BEAN_NAME = "viewResolver";

    private Map<String, MappingInfo> map = new HashMap<>();

    private Map<String, Object> ioc = new ConcurrentHashMap<>(255);


    public DispatcherServlet() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        appContext = new DefaultAppContext();
        initHandlerMapping();
        initBeanIoc();
        initInject();
    }


    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        View view = (View) ioc.get(VIEW_RESOLVER_BEAN_NAME);
        view.render(map,req,resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        map = null;
        ioc = null;
    }

    /**
     * 初始化映射uri 和 method
     */
    private void initHandlerMapping() {
        Set<Class<?>> set = ClassUtil.getClassSetByPackagename(appContext.getBasePackage()).stream().filter(i -> i.isAnnotationPresent(Controller.class)).collect(Collectors.toSet());
        set.stream().forEach(i -> {
            String var1 = "";
            Object newInstance = null;
            try {
                newInstance = i.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String key;
            Controller controller = i.getDeclaredAnnotation(Controller.class);
            key = controller.value();
            if (StringUtil.isEmpty(key)) {
                key = StringUtil.firstToLower(i.getSimpleName());
            }
            ioc.put(key, newInstance);
            Method[] methods = i.getDeclaredMethods();
            if (i.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = i.getDeclaredAnnotation(RequestMapping.class);
                String[] var3 = requestMapping.value();
                for (String var$ : var3) {
                    if (StringUtil.isEmpty(var$)) {
                        continue;
                    }
                    if (!var$.startsWith("/")) {
                        var1 = "/" + var$;
                    } else {
                        var1 = var$;
                    }
                    addMapping(var1, newInstance, methods);
                }
            } else {
                addMapping(var1, newInstance, methods);
            }

        });
    }

    /**
     *  获取 mapping
     * @param var1
     * @param newInstance
     * @param methods
     */
    private void addMapping(String var1, Object newInstance, Method[] methods) {
        String var2;
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping$ = method.getDeclaredAnnotation(RequestMapping.class);
                String[] var4 = requestMapping$.value();
                boolean renderJson = false;
                if (method.isAnnotationPresent(RenderJson.class)) {
                    renderJson = true;
                }
                for (String var$1 : var4) {
                    if (StringUtil.isNotEmpty(var$1) && !var$1.startsWith("/")) {
                        var2 = "/" + var$1;
                    } else {
                        var2 = var$1;
                    }
                    String url = var1 + var2;
                    MappingInfo mappingInfo = new MappingInfo(url, requestMapping$.method(), newInstance, method, renderJson);
                    map.put(url, mappingInfo);
                }
            }
        }
    }

    /**
     * 初始化ioc容器
     */
    private void initBeanIoc() {
        Set<Class<?>> set = ClassUtil.getClassSetByPackagename(appContext.getBasePackage()).stream().filter(i -> (i.isAnnotationPresent(Component.class) || i.isAnnotationPresent(Service.class))).collect(Collectors.toSet());
        set.stream().forEach(i -> {
            Component component = i.getDeclaredAnnotation(Component.class);
            Service service = i.getDeclaredAnnotation(Service.class);
            String value = null;
            if (component != null){
                value = component.value();
            }
            if (service != null){
                value = service.value();
            }
            if (StringUtil.isEmpty(value)) {
                value = StringUtil.firstToLower(i.getSimpleName());
            }
            try {
                ioc.put(value, i.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 依赖注入
     */
    private void initInject() {
        for (Map.Entry<String, Object> i : ioc.entrySet()) {
            Object target = i.getValue();
            Class<?> clazz = target.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Inject inject = field.getAnnotation(Inject.class);
                    String value = inject.value().trim();
                    if (StringUtil.isEmpty(value)) {
                        value = StringUtil.firstToLower(field.getType().getSimpleName());
                    }
                    field.setAccessible(true);
                    try {
                        field.set(target, ioc.get(value));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}