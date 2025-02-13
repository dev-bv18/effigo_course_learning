import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleNavbar = () => {
    setIsOpen(!isOpen);
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">
        <NavLink to="/" className="navbar-brand">
          CourseApp
        </NavLink>

        <button
          className="navbar-toggler"
          type="button"
          onClick={toggleNavbar}
          aria-controls="navbarNav"
          aria-expanded={isOpen ? "true" : "false"}
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className={`collapse navbar-collapse ${isOpen ? "show" : ""}`} id="navbarNav">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <NavLink to="/" className="nav-link" onClick={() => setIsOpen(false)}>
                Home
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/courses" className="nav-link" onClick={() => setIsOpen(false)}>
                Courses</NavLink>
            </li>
            <li className="nav-item">

                            <NavLink to="/registered-courses" className="nav-link" onClick={() => setIsOpen(false)}>
                                          Registered Courses
                                        </NavLink></li>
              <li className="nav-item">
                          <NavLink to="/users" className="nav-link" onClick={() => setIsOpen(false)}>
                            Users
                          </NavLink>
                        </li>
            <li className="nav-item">
              <NavLink to="/register" className="nav-link" onClick={() => setIsOpen(false)}>
                Register now!
              </NavLink>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
