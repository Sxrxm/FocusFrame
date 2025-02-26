import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginForm from "./Pages/LoginForm";
import RegisterForm from "./Pages/RegisterForm";
import "./App.css";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/register" element={<RegisterForm />} /> 
        <Route path="/register/:pacienteId" element={<RegisterForm />} /> 
        <Route path="/login" element={<LoginForm />} />
      </Routes>
    </Router>
  );
}

export default App;
