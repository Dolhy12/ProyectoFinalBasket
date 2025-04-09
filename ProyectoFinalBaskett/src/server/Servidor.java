package server;

import java.io.*;
import java.net.*;

public class Servidor extends Thread {
    private static final int PUERTO = 7000;
    private static final String NOMBRE_ARCHIVO = "Liga_respaldo.dat";
    private Socket socketCliente;
    
    public Servidor(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }
    
    public static void main(String[] args) {
        iniciarServidor();
    }
    
    private static void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en puerto: " + PUERTO);
            
            while(true) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("Conexión aceptada de: " + socketCliente.getInetAddress());
                new Servidor(socketCliente).start();
            }
        } catch(IOException ex) {
            System.err.println("Error al iniciar servidor: " + ex.getMessage());
        }
    }
    
    @Override
    public void run() {
        try (DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
             DataOutputStream salida = new DataOutputStream(new FileOutputStream(NOMBRE_ARCHIVO))) {
            
            copiarDatos(entrada, salida);
            
        } catch(IOException ex) {
            System.err.println("Error en comunicación: " + ex.getMessage());
        } finally {
            cerrarSocket();
        }
    }
    
    private void copiarDatos(DataInputStream entrada, DataOutputStream salida) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesLeidos;
        
        while((bytesLeidos = entrada.read(buffer)) != -1) {
            salida.write(buffer, 0, bytesLeidos);
        }
    }
    
    private void cerrarSocket() {
        try {
            if(socketCliente != null && !socketCliente.isClosed()) {
                socketCliente.close();
            }
        } catch(IOException ex) {
            System.err.println("Error al cerrar socket: " + ex.getMessage());
        }
    }
}