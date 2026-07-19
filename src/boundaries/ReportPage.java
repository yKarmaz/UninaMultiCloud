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
        // 1. Definiamo il layout del pannello principale
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 2. Creiamo il dataset con i dati di esempio (qui modificherai i dati)
        dataset = new DefaultCategoryDataset();
        configuraDatiEsempio();

        // 3. Creiamo il grafico a colonne (Bar Chart) usando la factory di JFreeChart
        JFreeChart grafico = ChartFactory.createBarChart(
                "Statistiche Generali di " + reportController.getUtenteLoggato().getUsername(), // Titolo del grafico
                "",                            // Etichetta asse X
                "Conteggio",                            // Etichetta asse Y
                dataset,                                // Il dataset con i dati
                PlotOrientation.VERTICAL,               // Orientamento delle colonne
                true,                                   // Mostra la legenda
                true,                                   // Attiva i tooltips al passaggio del mouse
                false                                   // Configurazione URL (non necessaria)
        );

        // 4. Inseriamo il grafico dentro il ChartPanel (il componente Swing di JFreeChart)
        chartPanel = new ChartPanel(grafico);
        chartPanel.setPreferredSize(new Dimension(800, 500));

        // Aggiungiamo il grafico al centro del nostro JPanel
        add(chartPanel, BorderLayout.CENTER);
    }

    /**
     * Modifica i valori all'interno di questo metodo per cambiare le colonne del grafico.
     * La struttura è: dataset.setValue(VALORE, "Legenda/Serie", "Etichetta Asse X");
     */
    private void configuraDatiEsempio() {
        // Esempio Serie 1: Elementi totali caricati per tipologia di Cloud o Media
        dataset.setValue(reportController.getNumeroContenutiPubblicati(reportController.getUtenteLoggato()), "Contenuti Pubblicati", "Contenuti Pubblicati");
        dataset.setValue(reportController.getNumeroElementiVisualizzati(reportController.getUtenteLoggato()), "Elementi Visualizzati", "Elementi Visualizzati");
        dataset.setValue(reportController.getNumeroVisualizzazioni(reportController.getUtenteLoggato()), "Numero Visualizzazioni", "Numero Visualizzazioni");

        // Esempio Serie 2: Playlist associate (Puoi rimuoverla o cambiarla)
            }

    /**
     * Metodo di utilità pubblica se avrai bisogno di aggiornare il grafico a runtime
     * passando nuovi dati da un controller.
     */
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