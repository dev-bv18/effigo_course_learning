import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./App.css";
import Navbar from "./Components/Navbar";
import Home from "./Components/Home";
import "bootstrap/dist/css/bootstrap.min.css";
import CourseList from "./Components/CourseList";
import UsersList from "./Components/UsersList";
import Register from "./Components/Register";
import RegisteredCourseList from "./Components/RegisteredCourseList";

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<Home/>} />
          <Route path="/courses" element={<CourseList />} />
          <Route path="/users" element={<UsersList/>}/>
          <Route path="/register" element={<Register/>}/>
          <Route path="/registered-courses" element={<RegisteredCourseList/>}/>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
