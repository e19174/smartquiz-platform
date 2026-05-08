// src/pages/StudentJoin.jsx
import { useState } from "react";
import API from "../services/api";

export default function StudentJoin({ onJoin }) {
  const [name, setName] = useState("");
  const [pin, setPin] = useState("");

  const joinQuiz = async () => {
    const res = await API.post(`/api/join?pin=${pin}&name=${name}`);
    onJoin(res.data);
  };

return (
<div className="app-container">
  <div className="card">
    <h1 className="title">Join Quiz</h1>
    <p className="subtitle">Enter your name and quiz PIN</p>
    <input
      placeholder="Your Name"
      onChange={(e) => setName(e.target.value)}
    />
    <input
      placeholder="Quiz PIN"
      onChange={(e) => setPin(e.target.value)}
    />  

    <button className="primary-btn" onClick={joinQuiz}>
      Join Quiz
    </button>
  </div>
</div>
);
}
