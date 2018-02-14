import enkan.system.Repl;
import enkan.system.command.JsonRequestCommand;
import enkan.system.command.MetricsCommandRegister;
import enkan.system.command.SqlCommand;
import enkan.system.devel.DevelCommandRegister;
import enkan.system.repl.JShellRepl;
import enkan.system.repl.PseudoRepl;
import enkan.system.repl.ReplBoot;
import enkan.system.repl.client.ReplClient;
import kotowari.system.KotowariCommandRegister;

public class ReplMain {
    public static void main(String[] args) throws Exception {
        //JShellRepl repl = new JShellRepl("net.unit8.rascaloid.RascaloidSystemFactory");
        PseudoRepl repl = new PseudoRepl("net.unit8.rascaloid.RascaloidSystemFactory");
        ReplBoot.start(repl,
                new KotowariCommandRegister(),
                r -> {
                    r.registerCommand("sql", new SqlCommand());
                    r.registerCommand("jsonRequest", new JsonRequestCommand());
                },
                new DevelCommandRegister(),
                new MetricsCommandRegister());

        new ReplClient().start(repl.getPort());
    }
}
