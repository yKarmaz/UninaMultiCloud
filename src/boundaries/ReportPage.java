package boundaries;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import controllers.ReportController;

public class ReportPage extends JPanel {

    private DefaultCategoryDataset dataset;
    private ChartPanel chartPanel;
    private ReportController reportController;
    private HomePage homepage;
    public ReportPage(ReportController reportController, HomePage homepage) {
    	this.reportController = reportController;
    	this.homepage = homepage;
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dataset = new DefaultCategoryDataset();
        configuraDati();

        //Creiamo il grafico a colonne (Bar Chart) usando la factory di JFreeChart
        JFreeChart grafico = ChartFactory.createBarChart(
                "Statistiche Generali di " + reportController.getUtenteLoggato().getUsername(), // Titolo del grafico
                "",                            			// Etichetta asse X
                "Conteggio",                            // Etichetta asse Y
                dataset,                                // Il dataset con i dati
                PlotOrientation.VERTICAL,               // Orientamento delle colonne
                true,                                   // Mostra la legenda
                true,                                   // Per l'hover del mouse
                false                                   // Configurazione URL (non necessaria)
        );

        //Inseriamo il grafico dentro il ChartPanel 
        chartPanel = new ChartPanel(grafico);
        chartPanel.setPreferredSize(new Dimension(800, 500));

        // Aggiungiamo il grafico al centro del nostro JPanel
        add(chartPanel, BorderLayout.CENTER);
    }

    private void configuraDati() {
        // Elementi totali caricati per tipologia
        dataset.setValue(reportController.getNumeroContenutiPubblicati(reportController.getUtenteLoggato()), "Contenuti Pubblicati", "Contenuti Pubblicati");
        dataset.setValue(reportController.getNumeroElementiVisualizzati(reportController.getUtenteLoggato()), "Elementi Visualizzati", "Elementi Visualizzati");
        dataset.setValue(reportController.getNumeroVisualizzazioni(reportController.getUtenteLoggato()), "Numero Visualizzazioni", "Numero Visualizzazioni");

            }

    
    public void aggiornaGrafico(DefaultCategoryDataset nuovoDataset) {
        this.dataset = nuovoDataset;
        // Ricostruisce il grafico interno al pannello
        JFreeChart nuovoGrafico = ChartFactory.createBarChart(
                "Statistiche Generali UninaMultiCloud", "Categoria", "Conteggio",
                dataset, PlotOrientation.VERTICAL, true, true, false
        );
        chartPanel.setChart(nuovoGrafico);
    }
}