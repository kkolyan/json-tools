package net.kkolyan.json2;

import net.kkolyan.json2.evaluation.SimpleEvaluationContext;

import java.io.StringReader;

/**
 * @author nplekhanov
 */
public class EvaluationDemo {
    public static void main(String[] args) {
        SimpleEvaluationContext context = new SimpleEvaluationContext();
        context.setValue("service", new Service());
        context.setValue("pupil", new Pupil());
        System.out.println(Json.getEvaluator().evaluate(new StringReader(
                "pupil.age = service.name.length();" +
                        "service.name;" +
                        "ws=[];" +
                        "ws[2] = {};" +
                        "ws[2][\"metatag\"] = \"no one else\";" +
                        "ws[2].payload={key1:123,key2:522,key3:5886};" +
                        "fff=\"key3\";" +
                        "ws[2].payload[fff].toString().value[2];"), null, context));
        System.out.println(context.getMap());
    }

    public static class Service {
        public String name = "A Name";
    }

    public static class Pupil {
        public int age;

        @Override
        public String toString() {
            return "Pupil{" +
                    "age=" + age +
                    '}';
        }
    }
}
