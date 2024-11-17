import React, { useState } from "react";
import agendar from './agendar.webp';

export default function LoginForm() {
  const [isSignUpMode, setIsSignUpMode] = useState(false);

  
  const toggleMode = () => setIsSignUpMode(!isSignUpMode);

  return (
    <main className={isSignUpMode ? "sign-up-mode" : ""}>
      <div className="box">
        <div className="inner-box">
          <div className="forms-wrap">
            
            {!isSignUpMode && (
              <form action="index.html" autoComplete="off" className="sign-in-form">
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
                      type="text"
                      minLength="4"
                      className="input-field"
                      autoComplete="off"
                      required
                    />
                    <label>Correo</label>
                  </div>
                  <div className="input-wrap">
                    <input
                      type="password"
                      minLength="4"
                      className="input-field"
                      autoComplete="off"
                      required
                    />
                    <label>Password</label>
                  </div>
                  <input type="submit" value="Iniciar Sesion" className="sign-btn" />
                  <p className="text">
                    ¿Olvidaste tu contraseña?
                    <a href="#">Recuperar</a>
                  </p>
                </div>
              </form>
            )}

            
            {isSignUpMode && (
              <form action="index.html" autoComplete="off" className="sign-up-form">
                <div className="logo">
                  <h4>Focus Frame</h4>
                </div>
                <div className="heading">
                  <h2>Registrarse</h2>
                  <h6>¿Ya tienes una cuenta?</h6>
                  <a href="#" onClick={toggleMode} className="toggle">
                    Iniciar Sesion
                  </a>
                </div>
                <div className="actual-form">
                  <div className="input-wrap">
                    <input
                      type="text"
                      minLength="4"
                      className="input-field"
                      autoComplete="off"
                      required
                    />
                    <label>Nombre</label>
                  </div>
                  <div className="input-wrap">
                    <input
                      type="email"
                      className="input-field"
                      autoComplete="off"
                      required
                    />
                    <label>Email</label>
                  </div>
                  <div className="input-wrap">
                    <input
                      type="password"
                      minLength="4"
                      className="input-field"
                      autoComplete="off"
                      required
                    />
                    <label>Password</label>
                  </div>
                  <input type="submit" value="Registrarme" className="sign-btn" />
                  <p className="text">
                    Al registrarme, acepto los
                    <a href="#">Términos de Servicio</a> y la
                    <a href="#">Política de Privacidad</a>
                  </p>
                </div>
              </form>
            )}
          </div>

          
          <div className="carousel-img">
            <div className="images-wrapper">
              <img src={agendar} className="image img-1" />
              <p className="image-text">Con <span className="highlight">FocusFrame</span>, administra
                 tu calendario, citas y archivos de cliente desde
                  una interfaz unificada. </p>
            </div>
            </div>
          </div>
        </div>
    
    </main>
  );
}
