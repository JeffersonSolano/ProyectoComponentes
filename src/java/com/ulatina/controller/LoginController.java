// Declaración del paquete en el que se encuentra la clase.
package com.ulatina.controller;

// Importación de las clases necesarias para el funcionamiento del controlador.
import com.ulatina.usuario.Usuario;
import com.ulatina.servicio.ServicioUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

// Anotación que define esta clase como un Managed Bean llamado "loginController".
@ManagedBean(name = "loginController")

// Anotación que define el alcance de la sesión (SessionScoped) para este Managed Bean.
// Los datos se mantienen durante toda la sesión del usuario.
@SessionScoped

// Declaración de la clase LoginController que implementa la interfaz Serializable.
public class LoginController implements Serializable {

    // Atributos de la clase para almacenar la información del usuario y datos relacionados.
    private int id;
    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;
    private Usuario usuarioTO = new Usuario();
    private List<Usuario> listaUsuarioTO = new ArrayList<>();
    private Usuario selectedUsuario = new Usuario();

    // Constructor de la clase LoginController.
    public LoginController() {
        // Inicialización de los atributos en el constructor.
        usuarioTO = new Usuario();
        selectedUsuario = new Usuario();
        listaUsuarioTO = new ArrayList<>();
    }

    // Método para autenticar al usuario e inicializar sus datos.
    public void ingresar() throws ClassNotFoundException {
        // Creación de instancias de los servicios necesarios.
        ServicioUsuario servicioUsuario = new ServicioUsuario();

        // Validación del usuario con el servicio de usuario.
        Usuario usuarioTORetorno = servicioUsuario.validarUsuario(this.getUsuarioTO().getCorreo(), this.getUsuarioTO().getContrasena());

        if (usuarioTORetorno != null) {
            // Si el usuario es válido, se asigna y se inicializan las listas con datos relevantes.
            this.usuarioTO = usuarioTORetorno;
            this.listaUsuarioTO = servicioUsuario.demeTodos();
            
            // Redirige a la página de inicio.
            this.redireccionar("/faces/home.xhtml");
        } else {
            // Si los datos no son válidos, muestra un mensaje de error.
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos inválidos", "La clave o correo no son correctos"));
        }
    }

    // Método para cerrar la sesión del usuario y redirigir a la página de inicio.
    public void cerrarSesion() {
        // Resetea los atributos relacionados con el usuario y las listas.
        this.usuarioTO = new Usuario();
        this.listaUsuarioTO = new ArrayList<>();
        // Redirige a la página de inicio.
        this.redireccionar("/faces/index.xhtml");
    }

    // Método para redirigir a una ruta específica.
    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            // Obtiene el objeto HttpServletRequest del contexto actual.
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            // Redirige a la ruta especificada concatenada con el contexto de la aplicación.
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {
            // Imprime el rastro del stack trace si ocurre una excepción durante la redirección.
            e.printStackTrace();
        }
    }

    // Método para insertar un nuevo usuario en el sistema.
    public void insertarUsuario() {
        // Crea una instancia del servicio de usuario.
        ServicioUsuario servicioUsuario = new ServicioUsuario();

        // Intenta insertar el usuario utilizando el servicio de usuario.
        if (servicioUsuario.insertar(usuarioTO)) {
            // Si la inserción es exitosa, muestra un mensaje de éxito y redirige a la página de inicio.
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Usuario Registrado", "El usuario fue registrado correctamente"));
            this.redireccionar("/faces/home.xhtml");
        } else {
            // Si la inserción falla, muestra un mensaje de error.
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Registro fallido", "Hubo un error al registrar el usuario"));
        }
    }

    // Método para obtener el nombre del usuario dado su ID.
    public String nombreUusario(int id) {
        // Crea una instancia del servicio de usuario y obtiene el nombre del usuario por ID.
        ServicioUsuario servicioUsuario = new ServicioUsuario();
        return servicioUsuario.usuarioPorId(id).getNombre();
    }


    public Usuario getUsuarioTO() {
        return usuarioTO;
    }

    public void setUsuarioTO(Usuario usuarioTO) {
        this.usuarioTO = usuarioTO;
    }

    public List<Usuario> getListaUsuarioTO() {
        return listaUsuarioTO;
    }

    public void setListaUsuarioTO(List<Usuario> listaUsuarioTO) {
        this.listaUsuarioTO = listaUsuarioTO;
    }

    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
}
