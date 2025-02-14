import React, { useState } from "react";
import Header from "../components/Header";
import ModalAgendarCita from "../components/ModalAgendarCita"; 
import ModalDetallesCita from "../components/ModalDetallesCita"; 

const Home = () => {
  const [isAgendarModalOpen, setIsAgendarModalOpen] = useState(false);
  const [isDetallesModalOpen, setIsDetallesModalOpen] = useState(false);
  const [selectedAppointment, setSelectedAppointment] = useState(null);

  const appointments = [
    {
      client: "Sara Mateus",
      status: "Completada",
      time: "1:00pm - 2:30pm",
      date: "23 Nov 2024",
    },
    {
      client: "Sara Mateus",
      status: "Pendiente",
      time: "3:00pm - 4:00pm",
      date: "24 Nov 2024",
    },
    {
      client: "Sara Mateus",
      status: "Cancelada",
      time: "10:00am - 11:30am",
      date: "25 Nov 2024",
    },
  ];

  const openDetallesModal = (appointment) => {
    setSelectedAppointment(appointment);
    setIsDetallesModalOpen(true);
  };

  return (
    <main className="xl:col-span-5 p-14 h-[100vh] overflow-y-scroll lg:ml-4 xl:ml-10 2xl:ml-12">
      <Header />

      {/* Sección de Resumen */}
      <section className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 mt-10 gap-8">
        {/* Total Citas */}
        <div className="bg-[#e5dae2] p-4 rounded-xl flex flex-col gap-4 shadow-2xl">
          <h4 className="font-bold-Urbanist text-gray-700 text-lg">Total Citas</h4>
          <span className="text-4xl font-bold text-gray-700">15</span>
        </div>

        {/* Citas Pendientes */}
        <div className="bg-white p-4 rounded-xl flex flex-col gap-4 shadow-2xl">
          <h4 className="font-bold-Urbanist text-gray-700 text-lg">Citas Pendientes</h4>
          <span className="text-4xl font-bold text-yellow-500">4</span>
        </div>

        {/* Citas Completadas */}
        <div className="bg-white p-4 rounded-xl flex flex-col gap-4 shadow-2xl text-lg">
          <h4 className="font-bold-Urbanist text-gray-700">Citas Completadas</h4>
          <span className="text-4xl font-bold text-green-500">10</span>
        </div>

        {/* Citas Canceladas */}
        <div className="bg-white p-4 rounded-xl flex flex-col gap-4 shadow-2xl text-lg">
          <h4 className="font-bold-Urbanist text-gray-700">Citas Canceladas</h4>
          <span className="text-4xl font-bold text-red-500">1</span>
        </div>
      </section>

      {/* Sección de Notas y Citas Recientes lado a lado */}
      <section className="grid grid-cols-1 md:grid-cols-2 gap-8 mt-10">
        {/* Sección de Citas Recientes */}
        <div>
          <h1 className="text-2xl font-bold-Urbanist mb-8">Citas Recientes</h1>
          {appointments.map((appointment, index) => (
            <div
              key={index}
              className="bg-white p-4 rounded-xl shadow-md flex justify-between items-center mb-4 cursor-pointer"
              onClick={() => openDetallesModal(appointment)}
            >
              <div>
                <h3 className="font-bold-Urbanist">{appointment.client}</h3>
                <p className="text-gray-500">
                  {appointment.date} | {appointment.time}
                </p>
              </div>
              <div>
                <span
                  className={`py-1 px-3 rounded-full text-sm font-medium ${
                    appointment.status === "Pendiente"
                      ? "bg-yellow-100 text-yellow-800"
                      : appointment.status === "Completada"
                      ? "bg-green-100 text-green-800"
                      : "bg-red-100 text-red-800"
                  }`}
                >
                  {appointment.status}
                </span>
              </div>
            </div>
          ))}
        </div>

        {/* Sección de Notas */}
        <div>
          <h1 className="text-2xl font-bold-Urbanist mb-8">Notas</h1>
          <div className="bg-white p-4 rounded-lg shadow-inner overflow-y-scroll max-h-96">
            {Array.from({ length: 5 }, (_, index) => (
              <div
                key={index}
                className="bg-[#e4d9e1] text-gray-500 p-4 sm:p-5 rounded-lg shadow-lg mb-4"
              >
                <h3 className="text-lg text-black font-Urbanist mb-2">
                  Nota {index + 1}
                </h3>
                <p className="text-sm text-gray-500">
                  Esta es la nota número {index + 1}. Muy bien
                </p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Modal de Agendar Cita */}
      <ModalAgendarCita
        isOpen={isAgendarModalOpen}
        onClose={() => setIsAgendarModalOpen(false)}
      />

      {/* Modal de Detalles de Cita */}
      <ModalDetallesCita
        isOpen={isDetallesModalOpen}
        onClose={() => setIsDetallesModalOpen(false)}
        appointment={selectedAppointment}
      />
    </main>
  );
};

export default Home;
