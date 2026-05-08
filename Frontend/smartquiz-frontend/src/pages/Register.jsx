import { useState } from "react";

export default function Register({ onRegister }) {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const register = async () => {

    const res = await fetch(
      "http://localhost:8080/api/auth/register",
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
      alert("Registration Failed");
      return;
    }

    alert("Registration Successful");

    onRegister();
  };

  return (
    <div>

      <h2>Admin Register</h2>

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

      <button onClick={register}>
        Register
      </button>

    </div>
  );
}