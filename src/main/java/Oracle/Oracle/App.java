package Oracle.Oracle;

import java.sql.*;
/** USO DE ORACLE **/
public class App {
	private static Connection conexion;

	public static void main(String[] args) {
		crearConexion();
		//crearTipoPersona();
		//crearTablaEstudiantes();
		//insertarEstudiantes("Alejandro","601178825");
		//eliminarEstudiantes("Alejandro");
		//mostrarEstudiantes();
		//mostrarEstudiantesNombre("K");
		//verTablas();
		//crearTipoParticipante();
		//crearTablaParticipantes();
		insertarParticipantes(2,"K","601178825");
		mostrarParticipantes();
		eliminarParticipante("Alejandro");
		mostrarParticipantes();
	}

	public static void crearConexion() {
    	try {
    		Class.forName("oracle.jdbc.driver.OracleDriver");
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS  as SYSDBA","1234");
			System.out.println("Conexion creada");
    	} catch (Exception e) { System.out.println(e.getMessage());}
    }
	public static void cerrarConexion() {
    	try {
			conexion.close();
    	} catch (Exception e) { System.out.println(e.getMessage());}
    }
	public static void crearTipoPersona() {
		try {
	        Statement st = conexion.createStatement();
	        String crearTabla = "CREATE TYPE PERSONA AS OBJECT (" +
	                                "nombre VARCHAR2(50)," +
	                                "telefono VARCHAR2(50))";
	        st.executeUpdate(crearTabla);
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
	public static void crearTipoParticipante() {
		try {
	        Statement st = conexion.createStatement();
	        String crearTabla = "CREATE TYPE PARTICIPANTE AS OBJECT (" +
	                                "id NUMBER," +
	                                "datos PERSONA)";
	        st.executeUpdate(crearTabla);
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
	public static void crearTablaEstudiantes() {
		try {
	        Statement st = conexion.createStatement();
	        String crearTabla = "CREATE TABLE ESTUDIANTES OF PERSONA";
	        st.executeUpdate(crearTabla);
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
	public static void insertarEstudiantes(String nombre,String telefono) {
		try {
			String insertSQL = "INSERT INTO ESTUDIANTES VALUES('" + nombre + "','" + telefono + "')";
			Statement st = conexion.createStatement();
			st.executeUpdate(insertSQL);
			System.out.println("Datos insertados");
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
	public static void eliminarEstudiantes(String nombre) {
		try {
			String deleteSQL = "DELETE FROM ESTUDIANTES E WHERE E.nombre = '" + nombre + "'";
			Statement st = conexion.createStatement();
			st.executeUpdate(deleteSQL);
			System.out.println("Datos eliminados");
		} catch (Exception e) { System.out.println(e.getMessage());}
	}	
	public static void mostrarEstudiantes() {
		try {
			Statement st = conexion.createStatement();
			System.out.println("CONTACTOS");
			ResultSet resul = st.executeQuery("SELECT * FROM ESTUDIANTES");
			while(resul.next()) {
				System.out.println("Nombre: " + resul.getString(1));
				System.out.println("Telefono: " + resul.getString(2));
			}
			resul.close();
			st.close();
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
	public static void mostrarEstudiantesNombre(String nombre) {
		try {
			Statement st = conexion.createStatement();
			System.out.println("CONTACTOS");
			ResultSet resul = st.executeQuery("SELECT * FROM ESTUDIANTES WHERE nombre='" + nombre + "'");
			while(resul.next()) {
				System.out.println("Nombre: " + resul.getString(1));
				System.out.println("Telefono: " + resul.getString(2));
			}
			resul.close();
			st.close();
		} catch (Exception e) { System.out.println(e.getMessage());}
	}

	public static void crearTablaParticipantes() {
		try {
	        Statement st = conexion.createStatement();
	        String crearTabla = "CREATE TABLE PARTICIPANTES OF PARTICIPANTE";
	        st.executeUpdate(crearTabla);
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
	public static void insertarParticipantes(int id,String nombre,String telefono) {
		try {
			String insertSQL = "INSERT INTO PARTICIPANTES VALUES(" + id + ",PERSONA('" + nombre + "','" + telefono + "'))";
			Statement st = conexion.createStatement();
			st.executeUpdate(insertSQL);
			System.out.println("Datos insertados");
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
	public static void mostrarParticipantes() {
		try {
			Statement st = conexion.createStatement();
			System.out.println("PARTICIPANTES");
			ResultSet resul = st.executeQuery("SELECT * FROM PARTICIPANTES");
			while(resul.next()) {
				System.out.println("Id: " + resul.getString(1));
				oracle.sql.STRUCT objeto = (oracle.sql.STRUCT) resul.getObject(2);
				Object[] atributos = objeto.getAttributes();
				System.out.println("Nombre: " + atributos[0]);
				System.out.println("Telefono: " + atributos[1]);
			}
			resul.close();
			st.close();
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
	public static void mostrarParticipantesNombre(String nombre) {
		try {
			Statement st = conexion.createStatement();
			System.out.println("PARTICIPANTES");
			ResultSet resul = st.executeQuery("SELECT * FROM PARTICIPANTES p WHERE p.datos.nombre='" + nombre + "'");
			while(resul.next()) {
				System.out.println("Id: " + resul.getString(1));
				oracle.sql.STRUCT objeto = (oracle.sql.STRUCT) resul.getObject(2);
				Object[] atributos = objeto.getAttributes();
				System.out.println("Nombre: " + atributos[0]);
				System.out.println("Telefono: " + atributos[1]);
			}
			resul.close();
			st.close();
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
	public static void eliminarParticipante(String nombre) {
		try {
			String deleteSQL = "DELETE FROM PARTICIPANTES P WHERE P.datos.nombre = '" + nombre + "'";
			Statement st = conexion.createStatement();
			st.executeUpdate(deleteSQL);
			System.out.println("Datos eliminados");
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
	public static void verTablas() {
		try {
			DatabaseMetaData metaData = conexion.getMetaData();
            ResultSet resul = metaData.getTables(null, "SYS", null, new String[]{"TABLE"});
            System.out.println("Tablas disponibles en la base de datos:");
            while (resul.next()) {
                String tableName = resul.getString("TABLE_NAME");
            	System.out.println(tableName);
            }
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
}
