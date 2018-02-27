package net.unit8.rascaloid;

import enkan.system.EnkanSystem;
import enkan.system.command.JsonRequestCommand;
import enkan.system.command.MetricsCommandRegister;
import enkan.system.command.SqlCommand;
import enkan.system.repl.PseudoRepl;
import enkan.system.repl.ReplBoot;
import kotowari.system.KotowariCommandRegister;

import java.lang.reflect.Field;

import static enkan.util.ReflectionUtils.tryReflection;

public class Main {
    public static void main(String[] args) {
        final PseudoRepl repl = new PseudoRepl("net.unit8.rascaloid.RascaloidSystemFactory");
        ReplBoot.start(repl,
            new KotowariCommandRegister(),
            r -> {
                r.registerCommand("sql", new SqlCommand());
                r.registerCommand("jsonRequest", new JsonRequestCommand());
            },
            new MetricsCommandRegister());

        tryReflection(() -> {
            Field f = PseudoRepl.class.getDeclaredField("system");
            f.setAccessible(true);
            EnkanSystem system = (EnkanSystem) f.get(repl);
            system.start();
            return true;
        });
    }
}
