import { useState } from "react";

import StudentJoin from "./pages/StudentJoin";
import QuizPlayer from "./pages/QuizPlayer";
import AdminDashboard from "./pages/AdminDashboard";

import Login from "./pages/Login";
import Register from "./pages/Register";

function App() {

  const [participant, setParticipant] = useState(null);

  const [mode, setMode] = useState(null);

  const [showRegister, setShowRegister] = useState(false);

  const [loggedIn, setLoggedIn] =
    useState(!!localStorage.getItem("token"));

  // HOME
  // HOME
if (!mode) {

  return (

    <div className="home-container">

      <div className="home-card">

        <h1 className="home-title">
          Live Quiz Platform
        </h1>

        <p className="home-subtitle">
          Real-time interactive quiz experience
        </p>

        <div className="home-buttons">

          <button
            className="home-btn student-home-btn"
            onClick={() => setMode("student")}
          >
             Student
          </button>

          <button
            className="home-btn host-home-btn"
            onClick={() => setMode("admin")}
          >
             Host
          </button>

        </div>

      </div>

    </div>
  );
}

  // ADMIN
  if (mode === "admin") {

    if (!loggedIn) {

      if (showRegister) {

        return (
          <Register
            onRegister={() => setShowRegister(false)}
          />
        );
      }

      return (
        <Login
          onLogin={() => setLoggedIn(true)}
          goToRegister={() => setShowRegister(true)}
        />
      );
    }

    return (
      <AdminDashboard
        onLogout={() => {

          localStorage.removeItem("token");

          setLoggedIn(false);

          setMode(null);
        }}
      />
    );
  }

  // STUDENT
  if (!participant) {

    return (
      <StudentJoin onJoin={setParticipant} />
    );
  }

  return (
    <QuizPlayer participant={participant} />
  );
}

export default App;