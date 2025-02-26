import React, { useState } from "react";
import agendar from "./../agendar.webp";
import axios from "axios";

export default function RegisterForm() {

  
  const [mensajeError, setMensajeError] = useState("");
  const [register, setRegister] = useState({
    username: "",  
    password: "",
  });

 

  const handleChange = (e) => {
    setRegister({
      ...register,
      [e.target.name]: e.target.value,
    });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setMensajeError("");

    

    try {
      const response = await axios.post(
        `http://localhost:8081/paciente/completar-perfil/1`,
        register
      );

      console.log("Perfil completado con éxito:", response.data);
      alert("Perfil completado con éxito!");
      setRegister({ username: "", password: "" });
    } catch (error) {
      console.error("Error al completar perfil", error);
      setMensajeError(
        error.response?.data || "Hubo un error al completar el perfil"
      );
    }
  };

  return (
    <main>
      <div className="box">
        <div className="inner-box">
          <div className="forms-wrap">
            <form onSubmit={handleRegister} autoComplete="off" className="sign-up-form">
              <div className="logo">
                <h4>Focus Frame</h4>
              </div>
              <div className="heading">
                <h2>Completar Perfil!</h2>
              </div>
              <div className="actual-form">
                <div className="input-wrap">
                  <input
                    type="username"
                    id="username"
                    name="username"
                    className="input-field"
                    autoComplete="off"
                    value={register.username}
                    onChange={handleChange}
                    required
                  />
                  <label>UserName</label>
                </div>
                <div className="input-wrap">
                  <input
                    type="password"
                    id="password"
                    name="password"
                    className="input-field"
                    autoComplete="off"
                    value={register.password}
                    onChange={handleChange}
                    required
                  />
                  <label>Contraseña</label>
                </div>
                <button type="submit" className="sign-btn">Guardar</button>
                {mensajeError && <p className="error-message">{mensajeError}</p>}
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
