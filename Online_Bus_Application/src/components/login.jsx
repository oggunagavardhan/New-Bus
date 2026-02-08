import { useState } from "react";
import { Container, Card, Button, Form, ToggleButton, ButtonGroup } from "react-bootstrap";
import { motion, AnimatePresence } from "framer-motion";
import "./login.css";

export default function Login({ onLogin }) {

  const [role, setRole] = useState("user");
  const [page, setPage] = useState("login");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [otp, setOtp] = useState("");

  /* ===== SEND OTP ===== */
  const sendOtp = async () => {

    if (!email) return alert("Enter email");

    try {

      const res = await fetch(
        "https://new-bus-1.onrender.com/api/auth/send-otp",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            email: email,
            role: role
          })
        }
      );

      alert(await res.text());
      setPage("otp");

    } catch {
      alert("Failed to send OTP");
    }
  };

  /* ===== REGISTER ===== */
  const registerUser = async () => {

    if (!name || !email) return alert("Fill all fields");

    try {

      const res = await fetch(
        "https://new-bus-1.onrender.com/api/auth/register",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ name, email, role: "user" })
        }
      );

      const msg = await res.text();

      if (msg === "REGISTERED_SUCCESSFULLY") {
        alert("🎉 Registration Success");
        setPage("login");
        setName("");
        setEmail("");
      } else alert(msg);

    } catch {
      alert("Registration failed");
    }
  };

  /* ===== VERIFY OTP ===== */
  const verifyOtp = async () => {

    try {

      const res = await fetch(
        `https://new-bus-1.onrender.com/api/auth/verify-otp?email=${email}&otp=${otp}`,
        { method: "POST" }
      );

      if ((await res.text()) === "SUCCESS") onLogin(role);
      else alert("Invalid OTP");

    } catch {
      alert("OTP verification failed");
    }
  };

  return (
    <Container fluid className="login-bg">
      <motion.div
        className="login-3d-wrapper"
        whileHover={{ rotateY: 10, rotateX: -8 }}
        transition={{ type: "spring", stiffness: 120 }}
      >
        <Card className="login-card-3d">
          <Card.Body>

            <h3 className="text-center mb-3">🚌 Online Bus Reservation</h3>

            <ButtonGroup className="w-100 mb-3">
              {["user", "admin"].map(r => (
                <ToggleButton
                  key={r}
                  type="radio"
                  variant={role === r ? "primary" : "outline-primary"}
                  checked={role === r}
                  onClick={() => { setRole(r); setPage("login"); }}
                >
                  {r.toUpperCase()}
                </ToggleButton>
              ))}
            </ButtonGroup>

            <AnimatePresence mode="wait">

              {page === "login" && (
                <motion.div key="login">
                  <Form>

                    <Form.Control
                      type="email"
                      placeholder="Enter Email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      className="mb-3"
                    />

                    <Button className="w-100" onClick={sendOtp}>
                      Send OTP
                    </Button>

                    {role === "user" && (
                      <div
                        className="text-center mt-3 link3d"
                        onClick={() => setPage("register")}
                      >
                        Create New Account?
                      </div>
                    )}

                  </Form>
                </motion.div>
              )}

              {page === "register" && (
                <motion.div key="register">
                  <Form>

                    <Form.Control
                      placeholder="Enter Name"
                      className="mb-2"
                      value={name}
                      onChange={(e) => setName(e.target.value)}
                    />

                    <Form.Control
                      type="email"
                      placeholder="Enter Email"
                      className="mb-3"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                    />

                    <Button className="w-100" onClick={registerUser}>
                      Register
                    </Button>

                    <div
                      className="text-center mt-3 link3d"
                      onClick={() => setPage("login")}
                    >
                      ← Back to Login
                    </div>

                  </Form>
                </motion.div>
              )}

              {page === "otp" && (
                <motion.div key="otp">
                  <Form>

                    <Form.Control
                      placeholder="Enter OTP"
                      className="mb-3"
                      value={otp}
                      onChange={(e) => setOtp(e.target.value)}
                    />

                    <Button className="w-100" onClick={verifyOtp}>
                      Verify OTP
                    </Button>

                  </Form>
                </motion.div>
              )}

            </AnimatePresence>

          </Card.Body>
        </Card>
      </motion.div>
    </Container>
  );
}
