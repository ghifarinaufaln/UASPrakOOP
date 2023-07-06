package uasprakpboghif;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UASprakPBOGhif {
    /**
     * @param args the command line arguments
     */
    // Beberapa parameter JDBC untuk koneksi ke DataBase
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/crud_tdl";
    static final String USER = "root";
    static final String PASS = "";
    
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);
    
      
    public static void main(String[] args) {
        
        try {
            // register driver
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            ArrayList<String> arr = new ArrayList<>();
            String sql = "SELECT *FROM tdl";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                arr.add(rs.getString("todo"));
            }
            while (!conn.isClosed()) {
                tampilanMenu(arr);
            }
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void tampilanMenu(ArrayList arr)throws SQLException, IOException {
        System.out.println("\n\t======= DATABASE TODO LIST =======");
        System.out.println("\t[1] Masukan TODO LIST Baru ");
        System.out.println("\t[2] Lihat TODO LIST");
        System.out.println("\t[3] Perbarui TODO LIST");
        System.out.println("\t[4] Hapus TODO LIST");
        System.out.println("\t[0] Keluar\n");
        System.out.println("PILIHAN : ");
        
        try {
            int pilih = Integer.parseInt(input.readLine());
            
            switch (pilih) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertTDL();
                    break;
                case 2:
                    showTDL();
                    break;
                case 3:
                    updateTDL();
                    break;
                case 4:
                    deleteTDL();
                    break;
                    
                default:
                System.err.println("Pilihan salah! Silahkan Masukan Angka [0-4]");
            }
       
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
    
    static void showTDL() {
        String sql = "SELECT *FROM tdl";
        
        try {
            rs = stmt.executeQuery(sql);
            
            System.out.println("======================");
            System.out.println("|     TODO LIST     |");
            System.out.println("======================");
            
            while (rs.next()) {
                System.out.println("===========================");
                int iD = rs.getInt("id");
                String toDo = rs.getString("todo");
                String kategori = rs.getString("kategori");
                String tgl = rs.getString("tanggal_selesai");
                String status = rs.getString("status");
                
                System.out.println(String.format("(%d). %s\n %s (%s)\n %s\n", iD, toDo, kategori, tgl, status));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            
    }
    
    static void insertTDL() {
        try {
            System.out.print("ID User: ");
            int iD = Integer.parseInt(input.readLine());
            System.out.print("TODO LIST: ");
            String toDo = input.readLine();
            System.out.print("Kategori: ");
            String kategori = input.readLine();
            System.out.print("Tanggal Selesai: ");
            String tgl = input.readLine();
            System.out.print("Status: ");
            String status = input.readLine();
            
            String sql = "INSERT INTO tdl (id, todo, kategori, tanggal_selesai, status) VALUE('%d', '%s', '%s', '%s', '%s')" ;
            sql = String.format(sql, iD, toDo, kategori, tgl, status);
            
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void updateTDL() {
        try {
            System.out.print("ID yang akan diedit: ");
            int iD = Integer.parseInt(input.readLine());
            System.out.print("Update TODO LIST: ");
            String toDo = input.readLine();
            System.out.print("Update Kategori: ");
            String kategori = input.readLine();
            System.out.print("Update Tanggal Selesai: ");
            String tgl = input.readLine();
            System.out.print("Update Status: ");
            String status = input.readLine();
            
            String sql = "UPDATE tdl SET todo='%s', kategori='%s', tanggal_selesai='%s', status='%s' WHERE id=%d" ;
            sql = String.format(sql, toDo, kategori, tgl, status, iD);
            
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void deleteTDL() {
        try {
            System.out.print("ID yang akan dihapus: ");
            int iD = Integer.parseInt(input.readLine());
            String sql = String.format("DELETE FROM tdl WHERE id=%d", iD);
            stmt.execute(sql);
            System.out.println("Data telah terhapus...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
