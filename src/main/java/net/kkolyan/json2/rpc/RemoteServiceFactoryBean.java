package net.kkolyan.json2.rpc;

import net.kkolyan.json2.evaluation.Evaluator;
import net.kkolyan.json2.serialization.Serializer;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

/**
 * @author nplekhanov
 */
public class RemoteServiceFactoryBean implements FactoryBean<Object>, InitializingBean {
    private String serviceName;
    private Class<?> serviceType;
    private Object object;
    private Evaluator evaluator;
    private Serializer serializer;
    private Shell shell;

    @Override
    public void afterPropertiesSet() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new RemoteMethodInterceptor(serviceName, shell, evaluator, serializer));
        enhancer.setSuperclass(serviceType);
        object = enhancer.create();
    }

    @Override
    public Object getObject() throws Exception {
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return serviceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Required
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Required
    public void setServiceType(Class<?> serviceType) {
        this.serviceType = serviceType;
    }

    @Required
    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Required
    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    @Required
    public void setShell(Shell shell) {
        this.shell = shell;
    }
}
