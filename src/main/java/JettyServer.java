
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class JettyServer {

	private static final Integer PORT = 8084;
	private static final String CONTEXT_PATH = "/milestone1";

	public static void main(String[] args) throws Exception {

		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);

		connector.setPort(PORT);
		connector.setHost("localhost");

		WebAppContext context = new WebAppContext();

		context.setConfigurations(
			 new Configuration[]{
				 new AnnotationConfiguration(),
				 new WebAppConfiguration(),
				 new WebXmlConfiguration(),
				 new WebInfConfiguration(),
				 new PlusConfiguration(),
				 new MetaInfConfiguration(),
				 new FragmentConfiguration(),
				 new EnvConfiguration()
			 }
		);

		context.setResourceBase("deploy");
		context.setContextPath(CONTEXT_PATH);
		context.setParentLoaderPriority(true);
		server.addConnector(connector);
		server.setHandler(context);

		server.start();

		System.out.println("\nMilestone 1 server ready on:\n" + server.getURI());

		server.join();
	}

}
