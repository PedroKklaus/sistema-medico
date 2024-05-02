import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListarConsultas {
    JFrame frame;
    ArrayList<Consulta> consultas;

    public ListarConsultas(ArrayList<Consulta> consultas) {
        this.consultas = consultas;
        frame = new JFrame();
        frame.setBounds(100, 100, 300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(consultas.size(), 1));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Consulta consulta : consultas) {
            consulta.verificarSeRealizada();
            LocalDateTime dataHoraConsulta = LocalDateTime.parse(consulta.dataConsulta, formatter);
            String data = dataHoraConsulta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String horario = dataHoraConsulta.format(DateTimeFormatter.ofPattern("HH:mm"));
            String status = dataHoraConsulta.isBefore(LocalDateTime.now()) ? "Finalizada" : "Marcada";
            JLabel lblConsulta = new JLabel("Paciente: " + consulta.paciente.getNome() + ", Data da Consulta: " + data + ", Horário: " + horario + ", Status: " + status);
            frame.getContentPane().add(lblConsulta);
        }
    }
}
