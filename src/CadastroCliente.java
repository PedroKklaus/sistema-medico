import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CadastroCliente {
    JFrame frame;
    ArrayList<Paciente> pacientes;

    public CadastroCliente(ArrayList<Paciente> pacientes) {
        this.pacientes = pacientes;
        frame = new JFrame();
        frame.setBounds(100, 100, 300, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.getContentPane().add(panel);

        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField();
        JLabel lblTelefone = new JLabel("Telefone:");
        JTextField txtTelefone = new JTextField();
        JLabel lblCep = new JLabel("CEP:");
        JTextField txtCep = new JTextField();
        JLabel lblRua = new JLabel("Rua:");
        JTextField txtRua = new JTextField();
        JLabel lblNumero = new JLabel("Número da Residência:");
        JTextField txtNumero = new JTextField();
        JLabel lblCidade = new JLabel("Cidade:");
        JTextField txtCidade = new JTextField();
        JLabel lblEstado = new JLabel("Estado:");
        JTextField txtEstado = new JTextField();
        JButton btnSalvar = new JButton("Salvar");

        // Adicionando evento de foco para o campo CEP
        txtCep.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Não é necessário fazer nada quando o campo ganha foco
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Quando o campo perde foco, realizar a consulta via CEP
                String cep = txtCep.getText().replaceAll("[^0-9]", "");
                if (cep.length() != 8) {
                    JOptionPane.showMessageDialog(frame, "CEP inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    String url = "https://viacep.com.br/ws/" + cep + "/json/";
                    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    if (conn.getResponseCode() == 200) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            response.append(line);
                        }
                        br.close();

                        JSONObject json = new JSONObject(response.toString());
                        String rua = json.getString("logradouro");
                        String cidade = json.getString("localidade");
                        String estado = json.getString("uf");

                        txtRua.setText(rua);
                        txtCidade.setText(cidade);
                        txtEstado.setText(estado);
                    } else {
                        JOptionPane.showMessageDialog(frame, "CEP não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                    conn.disconnect();
                } catch (IOException | JSONException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao consultar o CEP.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String telefone = txtTelefone.getText();
            String rua = txtRua.getText();
            int numeroCasa = Integer.parseInt(txtNumero.getText());
            String cidade = txtCidade.getText();
            String estado = txtEstado.getText();
            String cep = txtCep.getText();
            Endereco endereco = new Endereco(cidade, rua, numeroCasa, cep);
            Paciente paciente = new Paciente(nome, email, telefone, endereco);
            pacientes.add(paciente);
            frame.dispose();
        });

        panel.add(lblNome);
        panel.add(txtNome);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblTelefone);
        panel.add(txtTelefone);
        panel.add(lblCep);
        panel.add(txtCep);
        panel.add(lblRua);
        panel.add(txtRua);
        panel.add(lblNumero);
        panel.add(txtNumero);
        panel.add(lblCidade);
        panel.add(txtCidade);
        panel.add(lblEstado);
        panel.add(txtEstado);
        panel.add(btnSalvar);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new CadastroCliente(new ArrayList<>());
    }
}
