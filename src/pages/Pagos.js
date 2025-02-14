import React from "react";
import Header from "../components/Header";


const Pagos = () => {

  
  return (
    <main className="xl:col-span-5 p-14 h-[100vh] overflow-y-scroll lg:ml-4 xl:ml-10 2xl:ml-12">
      <Header />
      <div className="flex items-center">
        <div>
          <h3 className="font-Urbanist text-lg text-gray-900  py-4 px-6  ">
            Pagos pendientes
          </h3>
        </div>
        <div className="ml-2 h-px flex-1 bg-gray-300"></div>
      </div>
      <div className="bg-[#e4d9e1] rounded-lg  p-5">
              <h4 className="font-medium text-sm text-gray-700">Pagos pendientes</h4>
            </div>
    </main>
  );
};

export default Pagos;
