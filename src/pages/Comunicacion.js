import React from "react";
import { FaSearch,FaCircle } from "react-icons/fa"; 
import Header from "../components/Header";

const Comunicacion = () => {
  const contacts = [
    { name: "Sara Mateus", lastMessage: "Buenas tardes doctor...", status: "online", time: "15min" },
    { name: "Juan Pablo", lastMessage: "Encantado de verte ayer.", status: "offline", time: "1h" },
    { name: "Pedro Julio", lastMessage: "Hola, ¿cómo estás?", status: "online", time: "5min" },
    
  ];

  const messages = [
    { sender: "Sara Mateus", text: "Hola, doctor, ¿cómo está?", time: "12:30", sentByUser: false },
    { sender: "Tú", text: "Hola, Sara. Estoy bien, gracias. ¿En qué puedo ayudarte?", time: "12:31", sentByUser: true },
    { sender: "Sara Mateus", text: "Quería confirmar nuestra cita para la próxima semana.", time: "12:33", sentByUser: false },
    { sender: "Tú", text: "Claro que sí, está confirmada. ¿Algo más?", time: "12:35", sentByUser: true },
  ];

  return (
    <div className="xl:col-span-5 p-14 h-[100vh] overflow-y-scroll lg:ml-4 xl:ml-10 2xl:ml-12">
      <Header />
      {/* Contenedor principal */}
      <main className="h-[95vh] md: h-[95vh] grid grid-cols-8">
        {/* Barra lateral de contactos */}
        <section className=" col-span-2 p-8">
          <div className="mb-8">
            <h2 className="font-Urbanist text-lg text-gray-900 mb-4">Contactos</h2>
            <form>
              <div className="relative">
                <input
                  type="text"
                  placeholder="Buscar contacto"
                  className="w-full p-2 border rounded-lg focus:outline-none pr-8"
                />
                <FaSearch className="absolute right-3 top-2.5 text-gray-300" />
              </div>
            </form>
          </div>
          {/* Lista de contactos */}
          <ul className="space-y-4">
            {contacts.map((contact, index) => (
              <li key={index} className="flex items-center">
                <div className="relative">
                  <img
                    src="https://img.freepik.com/foto-gratis/chico-caucasico-atractivo-seguro-beige-pullon-sonriendo-ampliamente-mientras-esta-pie-contra-gris_176420-44508.jpg"
                    alt={contact.name}
                    className="w-10 h-10 object-cover rounded-full"
                  />
                  <FaCircle
                    className={`absolute text-green-600 right-0 bottom-0 ${
                      contact.status === "online" ? "visible" : "hidden"
                    }`}
                  />
                </div>
                <div className="ml-4 flex-1">
                  <p className="font-medium text-gray-900">{contact.name}</p>
                  <p className="text-sm text-gray-500 truncate">{contact.lastMessage}</p>
                </div>
                <span className="text-xs text-gray-400">{contact.time}</span>
              </li>
            ))}
          </ul>
        </section>

        {/* Área del chat activo */}
        <section className="bg-white col-span-6 flex flex-col">
          {/* Encabezado del chat */}
          <header className="p-4 bg-gray-100 border-b flex items-center">
            <img
              src= "https://img.freepik.com/foto-gratis/chico-caucasico-atractivo-seguro-beige-pullon-sonriendo-ampliamente-mientras-esta-pie-contra-gris_176420-44508.jpg"
              alt="Sara Mateus"
              className="rounded-full w-11 h-10 mr-4"
            />
            <h2 className="text-lg font-medium">Sara Mateus</h2>
          </header>

          {/* Mensajes */}
          <div className="flex-1 overflow-y-scroll p-4">
            {messages.map((message, index) => (
              <div
                key={index}
                className={`flex ${
                  message.sentByUser ? "justify-end" : "justify-start"
                } mb-4`}
              >
                <div
                  className={`max-w-xs p-3 rounded-lg ${
                    message.sentByUser
                      ? "bg-[#e5dae2] text-right"
                      : "bg-gray-200"
                  }`}
                >
                  <p className="text-sm">{message.text}</p>
                  <span className="text-xs text-gray-500 mt-2 block">
                    {message.time}
                  </span>
                </div>
              </div>
            ))}
          </div>

          {/* Input de mensaje */}
          <footer className="p-4 border-t flex items-center">
            <input
              type="text"
              placeholder="Escribe un mensaje"
              className="flex-1 p-2 border rounded-lg focus:outline-none"
            />
            <button className="ml-2 bg-[#9d7f97] text-white p-3 rounded-full">
              Enviar
            </button>
          </footer>
        </section>
      </main>
    </div>
  );
};

export default Comunicacion;
