import { useState } from "react";

export default function Login({ onLogin, goToRegister }) {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const login = async () => {

    const res = await fetch(
      "http://localhost:8080/api/auth/login",
      {
        method: "POST",

        headers: {
          "Content-Type": "application/json"
        },

        body: JSON.stringify({
          email,
          password
        })
      }
    );

    if (!res.ok) {
      alert("Invalid Credentials");
      return;
    }

    const data = await res.json();

    localStorage.setItem("token", data.token);

    onLogin();
  };

  return (
    <div>

      <h2>Admin Login</h2>

      <input
        placeholder="Email"
        onChange={(e) => setEmail(e.target.value)}
      />

      <br /><br />

      <input
        type="password"
        placeholder="Password"
        onChange={(e) => setPassword(e.target.value)}
      />

      <br /><br />

      <button onClick={login}>
        Login
      </button>

      <br /><br />

      <button onClick={goToRegister}>
        Create Account
      </button>

    </div>
  );
}