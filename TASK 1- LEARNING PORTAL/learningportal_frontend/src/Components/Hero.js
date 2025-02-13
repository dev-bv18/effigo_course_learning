import React from "react";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./Hero.css"; // Ensure this file contains the background image

const Hero = () => {
  const navigate = useNavigate();

  return (
    <div className="hero-section text-white text-center d-flex align-items-center">
      <div className="container">
        <h1 className="display-4 fw-bold">Discover Top Courses</h1>
        <p className="lead">
          Learn from industry experts and upgrade your skills today.
        </p>
        <button className="btn btn-outline-info btn-lg px-4 me-sm-3 fw-bold" onClick={() => navigate("/courses")}>
          Explore Courses
        </button>
      </div>
    </div>
  );
};

export default Hero;
