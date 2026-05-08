// src/pages/QuizPlayer.jsx
import { useEffect, useState } from "react";
import { connectSocket, subscribe } from "../services/socket";
import API from "../services/api";

export default function QuizPlayer({ participant }) {
  const [question, setQuestion] = useState(null);
  const [timer, setTimer] = useState(0);
  const [leaderboard, setLeaderboard] = useState([]);
  const [score, setScore] = useState(participant.score || 0);
  const [quizEnded, setQuizEnded] = useState(false);
  const [answered, setAnswered] = useState(false);

  const quizId = participant.quiz.id;

  useEffect(() => {
    connectSocket(() => {
      subscribe(`/topic/quiz/${quizId}/question`, (q) => {
  setQuestion(q);
  setAnswered(false);
});
      subscribe(`/topic/quiz/${quizId}/timer`, setTimer);
      subscribe(`/topic/quiz/${quizId}/leaderboard`, (data) => {

        setLeaderboard(data);

        const me = data.find(
          (p) => p.name === participant.studentName
        );

        if (me) {
          setScore(me.score);
        }
        });

      subscribe(`/topic/quiz/${quizId}/end`, () => {


  setQuizEnded(true);

  setQuestion(null);

});
      
    });
  }, []);




  const answer = async (option) => {

  console.log("BUTTON CLICKED");
  console.log("Option:", option);
  console.log("Question:", question);
  console.log("Question ID:", question?.id);
  if (answered) return;

  try {

    const res = await API.post(
      `/api/answer?participantId=${participant.id}&questionId=${question.id}&selectedAnswer=${option}`
    );

    setAnswered(true);
    console.log("SUCCESS:", res.data);

  } catch (err) {

    console.error("ERROR:", err);

  }
};

  const rank =
  leaderboard.findIndex(
    p => p.name === participant.studentName
  ) + 1;


  if (quizEnded) {

  return (

    <div className="app-container">

      <div className="card">

        <h1 className="title">
           Quiz Finished!
        </h1>

        <div className="dashboard-grid">

          <div className="info-box">
            <h3>Your Final Score</h3>
            <h1>{score}</h1>
          </div>

          <div className="info-box">
            <h3>Your Rank</h3>
            <h1>#{rank}</h1>
          </div>

        </div>

        <div className="leaderboard">

          <h2 className="title">
             Final Leaderboard
          </h2>

          {leaderboard.map((p, i) => (

            <div
              className="leaderboard-item"
              key={i}
            >

              <span>
                #{i + 1} {p.name}
              </span>

              <strong>{p.score}</strong>

            </div>

          ))}

        </div>

      </div>

    </div>
  );
}

if (!question && !quizEnded) {
  return <h2>Waiting for quiz...</h2>;
}
  return (
      <div className="app-container">
        <div className="card">
          <h1 className="title">Live Quiz</h1>
          <div className="dashboard-grid">
            <div className="info-box">
              <h3>Time Left</h3>
              <h2>{timer}s</h2>
            </div>
            <div className="info-box">
              <h3>Your Score</h3>

              <h2>{score}</h2>
            </div>
          </div>
          <div className="question-box">
            <h2 className="question-title">
              {question.questionText}
            </h2>
            <div className="answers-grid">
              <button
  className={`answer-btn ${answered ? "disabled-btn" : ""}`}
  disabled={answered}
  onClick={() => answer("A")}>
                {question.optionA}
              </button>
              <button
                className={`answer-btn ${answered ? "disabled-btn" : ""}`}
                disabled={answered}     
                onClick={() => answer("B")}>
                {question.optionB}
              </button>
              <button
                className={`answer-btn ${answered ? "disabled-btn" : ""}`}
                disabled={answered}
                onClick={() => answer("C")}>
                {question.optionC}
              </button>
              <button
                className={`answer-btn ${answered ? "disabled-btn" : ""}`}
                disabled={answered}           
                onClick={() => answer("D")}>
                {question.optionD}
              </button>
            </div>
          </div>
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