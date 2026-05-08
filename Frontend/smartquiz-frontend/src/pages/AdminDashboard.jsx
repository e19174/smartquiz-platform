// src/pages/AdminDashboard.jsx
import { useEffect, useState } from "react";
import { connectSocket, subscribe, sendMessage } from "../services/socket";

export default function AdminDashboard({ onLogout }) {
  const [question, setQuestion] = useState(null);
  const [timer, setTimer] = useState(0);
  const [leaderboard, setLeaderboard] = useState([]);
  const [stats, setStats] = useState({});
  const [participants, setParticipants] = useState(0);

  const [file, setFile] = useState(null);
  const [quiz, setQuiz] = useState(null);

  const token = localStorage.getItem("token");

  // Connect WebSocket ONLY after quiz is uploaded
  useEffect(() => {
    if (!quiz) return;

    connectSocket(() => {
      subscribe(`/topic/quiz/${quiz.id}/question`, setQuestion);
      subscribe(`/topic/quiz/${quiz.id}/timer`, setTimer);
      subscribe(`/topic/quiz/${quiz.id}/leaderboard`, setLeaderboard);
      subscribe(`/topic/quiz/${quiz.id}/stats`, setStats);
      subscribe(`/topic/quiz/${quiz.id}/participants`, setParticipants);

      console.log(" Admin WebSocket Connected");
    });

  }, [quiz]);

  // Upload quiz (CSV + create quiz + get PIN)
  const uploadQuiz = async () => {
  if (!file) {
    alert("Select CSV file");
    return;
  }

  // STEP 1 — Create Quiz
  const quizRes = await fetch("http://localhost:8080/api/quizzes", {
    method: "POST",
     headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    body: JSON.stringify({ title: "My Quiz" })
  });

  const quizData = await quizRes.json();
  setQuiz(quizData);

  // STEP 2 — Upload CSV
  const formData = new FormData();
  formData.append("file", file);

  await fetch(`http://localhost:8080/api/upload/${quizData.id}`, {
    method: "POST",
    headers: {
      "Authorization": `Bearer ${token}`
    },
    body: formData,
  });

  alert(`Quiz Created! PIN: ${quizData.pin}`);
};

  //  Start quiz using dynamic quiz.id
  const startQuiz = () => {
    if (!quiz) {
      alert("Upload quiz first!");
      return;
    }

    console.log(" Start clicked", quiz.id);

    sendMessage("/app/start", quiz.id);

    // reset UI (optional)
    setQuestion(null);
    setLeaderboard([]);
    setStats({});
  };

  return (
  <div className="app-container">

    <div className="card">

      <h1 className="title">Admin Dashboard</h1>

      <div className="top-actions">

        <button
          className="secondary-btn"
          onClick={onLogout}>
          Logout
        </button>

        <input
          type="file"
          onChange={(e) => setFile(e.target.files[0])}
        />

        <button
          className="primary-btn"
          onClick={uploadQuiz}>
          Upload Quiz
        </button>

      </div>

      {quiz && (
        <div className="pin-box">
          <h2>Quiz PIN</h2>
          <div className="pin-number">{quiz.pin}</div>
        </div>
      )}

      <div className="center" style={{ marginTop: "20px" }}>
        <button
          className="primary-btn"
          onClick={startQuiz}
          disabled={!quiz}>
          Start Quiz
        </button>
      </div>

      <div className="dashboard-grid">

        <div className="info-box">
          <h3>Participants</h3>
          <h2>{participants}</h2>
        </div>

        <div className="info-box">
          <h3>Timer</h3>
          <h2>{timer}</h2>
        </div>

      </div>

      {question && (
        <div className="question-box">

          <h2 className="question-title">
            {question.questionText}
          </h2>

          <div className="dashboard-grid">

            <div className="info-box">
              <h3>{question.optionA}</h3>
              <h2>{stats.optionA || 0}</h2>
            </div>

            <div className="info-box">
              <h3>{question.optionB}</h3>
              <h2>{stats.optionB || 0}</h2>
            </div>

            <div className="info-box">
              <h3>{question.optionC}</h3>
              <h2>{stats.optionC || 0}</h2>
            </div>

            <div className="info-box">
              <h3>{question.optionD}</h3>
              <h2>{stats.optionD || 0}</h2>
            </div>

          </div>

        </div>
      )}

      <div className="leaderboard">

        <h2 className="title">Leaderboard</h2>

        {leaderboard.map((p, i) => (
          <div className="leaderboard-item" key={i}>
            <span>{p.name}</span>
            <strong>{p.score}</strong>
          </div>
        ))}

      </div>

    </div>
  </div>
);

}