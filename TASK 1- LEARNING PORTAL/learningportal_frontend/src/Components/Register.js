import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

const Register = () => {
  const [formData, setFormData] = useState({
    selectedUser: "",
    selectedCourse: "",
  });

  const [users, setUsers] = useState([]);
  const [courses, setCourses] = useState([]);
  const [message, setMessage] = useState("");

  // Fetch users and courses on component mount
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await fetch("http://localhost:8080/users-details/get-users");
        if (response.ok) {
          const data = await response.json();
          setUsers(data);
        } else {
          console.error("Failed to fetch users");
        }
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    };

    const fetchCourses = async () => {
      try {
        const response = await fetch("http://localhost:8080/course-details/get-courses");
        if (response.ok) {
          const data = await response.json();
          setCourses(data);
        } else {
          console.error("Failed to fetch courses");
        }
      } catch (error) {
        console.error("Error fetching courses:", error);
      }
    };

    fetchUsers();
    fetchCourses();
  }, []);

  // Handle form field changes
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.selectedUser || !formData.selectedCourse) {
      setMessage("Please select a user and a course.");
      return;
    }

    const registrationPayload = {
      user: { id: Number(formData.selectedUser)},
      course: { id: Number(formData.selectedCourse) }
    };
 console.log(registrationPayload)
    try {
      const response = await fetch("http://localhost:8080/registered-courses-details/register-course", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(registrationPayload),
      });

      if (response.ok) {
        setMessage("User successfully registered for the course!");
        setFormData({ selectedUser: "", selectedCourse: "" });
      } else {
        const errorData = await response.json();
        setMessage(`Failed to register: ${errorData.message || "Unknown error"}`);
      }
    } catch (error) {
      console.error("Error:", error);
      setMessage("Registered Course already exists!");
    }
  };

  return (
    <div className="register-container">
      <div className="overlay">
        <div className="card p-4 shadow-lg" style={{ width: "350px" }}>
          <h2 className="text-center mb-4 text-dark">Register for a Course</h2>

          {message && <p className="alert alert-info text-center">{message}</p>}

          <form onSubmit={handleSubmit}>
            {/* User Dropdown */}
            <div className="mb-3">
              <label className="form-label text-dark">Select User</label>
              <select
                name="selectedUser"
                className="form-select"
                value={formData.selectedUser}
                onChange={handleChange}
                required
              >
                <option value="">Select a user</option>
                {users.map((user) => (
                  <option key={user.id} value={user.id}> {/* Must use `id` */}
                    {user.userName} ({user.userRole})
                  </option>
                ))}
              </select>
            </div>

            {/* Course Dropdown */}
            <div className="mb-3">
              <label className="form-label text-dark">Select Course</label>
              <select
                name="selectedCourse"
                className="form-select"
                value={formData.selectedCourse}
                onChange={handleChange}
                required
              >
                <option value="">Select a course</option>
                {courses.map((course) => (
                  <option key={course.id} value={course.id}> {/* Must use `id` */}
                    {course.courseTitle}
                  </option>
                ))}
              </select>
            </div>

            <button type="submit" className="btn btn-dark w-100">
              Register
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Register;
