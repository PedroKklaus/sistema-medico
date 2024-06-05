import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Endereco {
    String cidade;
    String rua;
    int numeroCasa;
    String cep;

    public Endereco(String cidade, String rua, int numeroCasa, String cep) {
        this.cidade = cidade;
        this.rua = rua;
        this.numeroCasa = numeroCasa;
        this.cep = cep;
    }

    // Getters
    public String getCidade() {
        return cidade;
    }

    public String getRua() {
        return rua;
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }

    public String getCep() {
        return cep;
    }

    // Setters
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setNumeroCasa(int numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}

class Paciente {
    String nome;
    String email;
    String telefone;
    Endereco endereco;

    public Paciente(String nome, String email, String telefone, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}


class Consulta {
    Paciente paciente;
    String dataConsulta;
    boolean realizada;

    public Consulta(Paciente paciente, String dataConsulta) {
        this.paciente = paciente;
        this.dataConsulta = dataConsulta;
        this.realizada = false;
    }

    public void marcarComoRealizada() {
        this.realizada = true;
    }

    @Override
    public String toString() {
        String status = realizada ? "Finalizada" : "Marcada";
        return "Paciente: " + paciente.getNome() + ", Data da Consulta: " + dataConsulta + ", Status: " + status;
    }

    public void verificarSeRealizada() {
    }
}
class CriarConsulta {
    JFrame frame;
    ArrayList<Paciente> pacientes;
    ArrayList<Consulta> consultas;

    public CriarConsulta(ArrayList<Paciente> pacientes, ArrayList<Consulta> consultas) {
        this.pacientes = pacientes;
        this.consultas = consultas;
        frame = new JFrame();
        frame.setBounds(100, 100, 300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(4, 2));

        JLabel lblPaciente = new JLabel("Paciente:");
        JComboBox<Paciente> cbPacientes = new JComboBox<>(pacientes.toArray(new Paciente[0]));
        JLabel lblDataConsulta = new JLabel("Data da Consulta:");
        JTextField txtDataConsulta = new JTextField();
        JLabel lblHorarioConsulta = new JLabel("Horário da Consulta:");
        JTextField txtHorarioConsulta = new JTextField();
        JButton btnSalvar = new JButton("Salvar");

        btnSalvar.addActionListener(e -> {
            Paciente pacienteSelecionado = (Paciente) cbPacientes.getSelectedItem();
            String dataConsulta = txtDataConsulta.getText();
            String horarioConsulta = txtHorarioConsulta.getText();
            Consulta consulta = new Consulta(pacienteSelecionado, dataConsulta + " " + horarioConsulta);
            consultas.add(consulta);
            frame.dispose();
        });

        frame.getContentPane().add(lblPaciente);
        frame.getContentPane().add(cbPacientes);
        frame.getContentPane().add(lblDataConsulta);
        frame.getContentPane().add(txtDataConsulta);
        frame.getContentPane().add(lblHorarioConsulta);
        frame.getContentPane().add(txtHorarioConsulta);
        frame.getContentPane().add(btnSalvar);
    }
}


class ListarPacientes {
    JFrame frame;
    ArrayList<Paciente> pacientes;

    public ListarPacientes(ArrayList<Paciente> pacientes) {
        this.pacientes = pacientes;
        frame = new JFrame();
        frame.setBounds(100, 100, 300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(pacientes.size(), 1));

        for (Paciente paciente : pacientes) {
            JButton btnPaciente = new JButton(paciente.getNome());
            btnPaciente.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, "Nome: " + paciente.getNome() + "\nEmail: " + paciente.email + "\nTelefone: " + paciente.telefone + "\nEndereço: " + paciente.endereco.rua + ", " + paciente.endereco.numeroCasa + ", " + paciente.endereco.cidade + ", " + paciente.endereco.cep);
            });
            frame.getContentPane().add(btnPaciente);
        }
    }
}

public class Home {
    private JFrame frame;
    private ArrayList<Paciente> pacientes = new ArrayList<>();
    private ArrayList<Consulta> consultas = new ArrayList<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Home window = new Home();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Home() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(2, 1));

        JButton btnCadastrarPaciente = new JButton("Cadastrar Paciente");
        btnCadastrarPaciente.addActionListener(e -> {
            CadastroCliente cadastroCliente = new CadastroCliente(pacientes);
            cadastroCliente.frame.setVisible(true);
        });
        frame.getContentPane().add(btnCadastrarPaciente);

        JButton btnCriarConsulta = new JButton("Criar Consulta");
        btnCriarConsulta.addActionListener(e -> {
            CriarConsulta criarConsulta = new CriarConsulta(pacientes, consultas);
            criarConsulta.frame.setVisible(true);
        });
        frame.getContentPane().add(btnCriarConsulta);

        JButton btnListarPacientes = new JButton("Listar Pacientes");
        btnListarPacientes.addActionListener(e -> {
            ListarPacientes listarPacientes = new ListarPacientes(pacientes);
            listarPacientes.frame.setVisible(true);
        });
        frame.getContentPane().add(btnListarPacientes);

        JButton btnListarConsultas = new JButton("Listar Consultas");
        btnListarConsultas.addActionListener(e -> {
            ListarConsultas listarConsultas = new ListarConsultas(consultas);
            listarConsultas.frame.setVisible(true);
        });
        frame.getContentPane().add(btnListarConsultas);
    }
}
