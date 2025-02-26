import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom"; 
import agendar from "./../agendar.webp";

export default function LoginForm() {
  
  const [mensajeError, setMensajeError] = useState("");
  const [password, setPasswordValue] = useState("");
  const [email, setEmailValue] = useState("");

  
  const navigate = useNavigate();

  
  const handlePasswordChange = (e) => {
    setPasswordValue(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmailValue(e.target.value);
  };

  //  Iniciar Sesión
  const handleLogin = async (e) => {
    e.preventDefault();
    console.log("Datos enviados: " + email + " " + password);

    const data = {
      email: email,
      password: password
    };

    try {
      const response = await axios.post("http://localhost:8081/auth/login", data);

      if (response.data && response.data.token) {

        // Guardar token 
        localStorage.setItem("token", response.data.token);
        
        alert("Login exitoso");
        console.log("Token recibido:", response.data.token);

        // Redirigir 
        navigate("/dashboard");
      } else {
        alert("Usuario o contraseña incorrectos");
      }
    } catch (error) {
      if (error.response) {

        // Capturar errores específicos del backend
        if (error.response.status === 401) {
          setMensajeError("Usuario o contraseña incorrectos");
        } else if (error.response.status === 500) {
          setMensajeError("Error en el servidor, intenta más tarde");
        } else {
          setMensajeError("Error desconocido");
        }
      } else {
        setMensajeError("No se pudo conectar con el servidor");
      }
    }
  };

  return (
    <main>
      <div className="box">
        <div className="inner-box">
          <div className="forms-wrap">
            <form onSubmit={handleLogin} autoComplete="off" className="sign-in-form">
              <div className="logo">
                <h4>Focus Frame</h4>
              </div>
              <div className="heading">
                <h2>¡Bienvenidos!</h2>
                <h6>¿No estás registrado?</h6>
                <a href="/register" className="toggle">Registrarse</a>
              </div>
              <div className="actual-form">
                <div className="input-wrap">
                  <input
                    type="email"
                    className="input-field"
                    autoComplete="off"
                    value={email}
                    onChange={handleEmailChange}
                    required
                  />
                  <label>Correo</label>
                </div>
                <div className="input-wrap">
                  <input
                    type="password"
                    className="input-field"
                    autoComplete="off"
                    value={password}
                    onChange={handlePasswordChange}
                    required
                  />
                  <label>Contraseña</label>
                </div>
                <button type="submit" className="sign-btn">Iniciar Sesión</button>
                {mensajeError && <p className="error-message">{mensajeError}</p>}
                <p className="text">
                  ¿Olvidaste tu contraseña? <a href="#">Recuperar</a>
                </p>
              </div>
            </form>
          </div>

          {/* Sección de Imagen */}
          <div className="carousel-img">
            <div className="images-wrapper">
              <img src={agendar} className="image img-1" alt="Focus Frame" />
              <p className="image-text">
                Con <span className="highlight">FocusFrame</span>, administra tu
                calendario, citas y archivos de cliente desde una interfaz
                unificada.
              </p>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}
