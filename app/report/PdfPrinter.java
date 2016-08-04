package report;

import models.Rapport;
import org.pentaho.reporting.engine.classic.core.DataFactory;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.DriverConnectionProvider;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.SQLReportDataFactory;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

import java.io.File;
import java.io.IOException;
import java.util.Map;


/**
 * @author user
 */
public class PdfPrinter extends AbstractReportGenerator {

    public static String QUERY_NAME;
    public static String URL;
    public static String QUERY;
    public static String temp_pdf = play.Configuration.root().getString("Tmp_pdf");
    private static String template = play.Configuration.root().getString("Template");
    private static String host = play.Configuration.root().getString("dhost");
    private static String user = play.Configuration.root().getString("duser");
    private static String pass = play.Configuration.root().getString("dpassword");

    /**
     *
     * @param rapport
     * @throws IllegalArgumentException
     * @throws IOException
     * @throws ReportProcessingException
     */
    public static void printer(String codeRapport, String reference, Rapport rapport) throws IllegalArgumentException, IOException, ReportProcessingException {

        PdfPrinter.URL = template + rapport.getTemplate();
        PdfPrinter.QUERY_NAME = rapport.getLibelleRapport();
        PdfPrinter.QUERY = rapport.getTmpQuery();

        PdfPrinter printer = new PdfPrinter();

        String subst = ".prpt";
        String nomTemplate = rapport.getTemplate();
        String nomRapport = nomTemplate.replace(subst, "");

        // final File outputFilename = new File(temp_pdf + nomRapport + '_' + DateTime.now().toString("YYYY-MM-dd") + ".pdf");

        final File outputFilename = new File(temp_pdf + reference + ".pdf");

        // Generate the report
        printer.generateReport(AbstractReportGenerator.OutputType.PDF, outputFilename);
        System.err.println("Le rapport a été généré :  [" + outputFilename.getAbsolutePath() + "]");
    }


    @Override
    public MasterReport getReportDefinition() {
        try {
            final ClassLoader classloader = this.getClass().getClassLoader();
            final String reportDefinitionURL = URL;

            final ResourceManager resourceManager = new ResourceManager();
            final Resource directly = resourceManager.createDirectly(reportDefinitionURL, MasterReport.class);
            MasterReport report = (MasterReport) directly.getResource();
            report.setQuery(QUERY_NAME);
            return report;
        } catch (ResourceException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return
     */
    @Override
    public DataFactory getDataFactory() {
        final DriverConnectionProvider psotgresDriverConnectionProvider = new DriverConnectionProvider();

        psotgresDriverConnectionProvider.setDriver("org.postgresql.Driver");
        psotgresDriverConnectionProvider.setUrl(host);
        psotgresDriverConnectionProvider.setProperty("user", user);
        psotgresDriverConnectionProvider.setProperty("password", pass);

        final SQLReportDataFactory dataFactory = new SQLReportDataFactory(psotgresDriverConnectionProvider);
        dataFactory.setQuery(QUERY_NAME, QUERY);
        return dataFactory;

    }

    /**
     * @return
     */
    @Override
    public Map<String, Object> getReportParameters() {
        return null;
    }

}
