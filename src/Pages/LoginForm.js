import React, { useState } from "react";
import agendar from "./../agendar.webp";

export default function LoginForm() {
  {
    /* Ingresar */
  }
  const [isSignUpMode, setIsSignUpMode] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [mensajeError, setMensajeError] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
    };

    try {
      const response = await fetch(
        "http://localhost:8080/auth/login",
        requestOptions
      );
      if (response.ok) {
        const data = await response.json();
        localStorage.setItem("token", data.token);
        console.log("Inicio de sesión exitoso:", data);
      } else {
        const errorData = await response.json();
        setMensajeError(errorData.message || "Error en el inicio de sesión");
      }
    } catch (error) {
      setMensajeError("Error de conexión con el servidor");
      console.error("Error de conexión:", error);
    }
  };

  const toggleMode = () => setIsSignUpMode(!isSignUpMode);

  
  /* Registro */

  const [username, setUserName] = useState("");
  const [registerEmail, setRegisterEmail] = useState("");
  const [registerPassword, setRegisterPassword] = useState("");
  const [registerError, setRegisterError] = useState("");

  const handleRegister = async (e) => {
    e.preventDefault();
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        username,
        email: registerEmail,
        password: registerPassword,
      }),
    };

    try {
      const response = await fetch(
        "http://localhost:8080/auth/register",
        requestOptions
      );
      if (response.ok) {
        const data = await response.json();
        console.log("Registro exitoso:", data);
        setIsSignUpMode(false); 
      } else {
        const errorData = await response.json();
        setRegisterError(errorData.message || "Error en el registro");
      }
    } catch (error) {
      setRegisterError("Error de conexión con el servidor");
      console.error("Error de conexión:", error);
    }
  };

  return (
    <main className={isSignUpMode ? "sign-up-mode" : ""}>
      <div className="box">
        <div className="inner-box">
          <div className="forms-wrap">
            {/* Formulario de Inicio de Sesión */}
            {!isSignUpMode && (
              <form
                onSubmit={handleLogin}
                autoComplete="off"
                className="sign-in-form"
              >
                <div className="logo">
                  <h4>Focus Frame</h4>
                </div>
                <div className="heading">
                  <h2>¡Bienvenidos!</h2>
                  <h6>¿No estás registrado?</h6>
                  <a href="#" onClick={toggleMode} className="toggle">
                    Registrarse
                  </a>
                </div>
                <div className="actual-form">
                  <div className="input-wrap">
                    <input
                      type="email"
                      className="input-field"
                      autoComplete="off"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
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
                      onChange={(e) => setPassword(e.target.value)}
                      required
                    />
                    <label>Contraseña</label>
                  </div>
                  <button type="submit" className="sign-btn">
                    Iniciar Sesión
                  </button>
                  {mensajeError && (
                    <p className="error-message">{mensajeError}</p>
                  )}
                  <p className="text">
                    ¿Olvidaste tu contraseña? <a href="#">Recuperar</a>
                  </p>
                </div>
              </form>
            )}

            {/* Formulario de Registro */}
            {isSignUpMode && (
              <form
                onSubmit={handleRegister}
                autoComplete="off"
                className="sign-up-form"
              >
                <div className="logo">
                  <h4>Focus Frame</h4>
                </div>
                <div className="heading">
                  <h2>Registrarse</h2>
                  <h6>¿Ya tienes una cuenta?</h6>
                  <a href="#" onClick={toggleMode} className="toggle">
                    Iniciar Sesión
                  </a>
                </div>
                <div className="actual-form">
                  <div className="input-wrap">
                    <input
                      type="text"
                      className="input-field"
                      autoComplete="off"
                      value={username}
                      onChange={(e) => setUserName(e.target.value)}
                      required
                    />
                    <label>Nombre</label>
                  </div>
                  <div className="input-wrap">
                    <input
                      type="email"
                      className="input-field"
                      autoComplete="off"
                      value={registerEmail}
                      onChange={(e) => setRegisterEmail(e.target.value)}
                      required
                    />
                    <label>Correo</label>
                  </div>
                  <div className="input-wrap">
                    <input
                      type="password"
                      className="input-field"
                      autoComplete="off"
                      value={registerPassword}
                      onChange={(e) => setRegisterPassword(e.target.value)}
                      required
                    />
                    <label>Contraseña</label>
                  </div>
                  <button type="submit" className="sign-btn">
                    Registrarme
                  </button>
                  {registerError && (
                    <p className="error-message">{registerError}</p>
                  )}
                  <p className="text">
                    Al registrarme, acepto los{" "}
                    <a href="#">Términos de Servicio</a> y la{" "}
                    <a href="#">Política de Privacidad</a>
                  </p>
                </div>
              </form>
            )}
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
