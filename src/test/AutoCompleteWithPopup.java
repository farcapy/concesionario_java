package test;


import conn.Conexion;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoCompleteWithPopup extends JFrame {

    Conexion conectar = new Conexion();
    private JTextField textField;
    private JPopupMenu popupMenu;
    private Timer timer;

    public AutoCompleteWithPopup() {
        // Configuración básica del JFrame
        setTitle("Autocompletar Marcas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        textField = new JTextField();
        textField.setBounds(50, 50, 300, 30);
        popupMenu = new JPopupMenu();

        // Añadir el DocumentListener al JTextField
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                restartTimer();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                restartTimer();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                restartTimer();
            }
        });
        
        // Añadir un KeyListener para detectar cuando se presiona Enter
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (popupMenu.isVisible()) {
                        popupMenu.setVisible(false);
                    }
                }
            }
        });
        
        // Configurar el Timer con un retraso de 300ms (0.3 segundos)
        timer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSuggestions();
            }
        });
        timer.setRepeats(false);

        // Añadir el JTextField al JFrame
        add(textField);
        setVisible(true);
    }
    
    private void restartTimer() {
        if (timer.isRunning()) {
            timer.restart();
        } else {
            timer.start();
        }
    }

    private void updateSuggestions() {
        String input = textField.getText();
        if (input.isEmpty()) {
            popupMenu.setVisible(false);
            return;
        }

        // Obtener sugerencias de la base de datos
        List<String> suggestions = getSuggestionsFromDB(input);

        // Limpiar el PopupMenu antes de añadir nuevas sugerencias
        popupMenu.removeAll();

        for (String suggestion : suggestions) {
            JMenuItem item = new JMenuItem(suggestion);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textField.setText(suggestion);
                    popupMenu.setVisible(false);
                }
            });
            popupMenu.add(item);
        }

        // Mostrar el PopupMenu justo debajo del JTextField
        if (suggestions.size() > 0) {
            popupMenu.show(textField, 0, textField.getHeight());
            textField.requestFocusInWindow();  // Mantener el foco en el JTextField
        } else {
            popupMenu.setVisible(false);
        }
    }

    private List<String> getSuggestionsFromDB(String input) {
        List<String> suggestions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Conectar a la base de datos
            connection = conectar.conectarMySQL(); // Implementa tu método de conexión aquí
            String sql = "SELECT DISTINCT marca FROM vehiculos WHERE marca LIKE ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, input + "%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                suggestions.add(resultSet.getString("marca"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return suggestions;
    }

    // Ejemplo de método de conexión a MySQL
    private Connection conectarMySQL() {
        // Implementar la lógica de conexión a la base de datos MySQL aquí
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AutoCompleteWithPopup::new);
    }
}
