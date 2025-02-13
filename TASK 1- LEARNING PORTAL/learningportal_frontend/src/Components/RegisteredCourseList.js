import React, { useEffect, useState } from "react";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";

const RegisteredCourseList = () => {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [categoryImages, setCategoryImages] = useState({});

  useEffect(() => {
    fetchRegisteredCourses();
  }, []);

  const fetchRegisteredCourses = () => {
    setLoading(true);
    axios.get("http://localhost:8080/registered-courses-details/get-registered-courses")
      .then((response) => {
        setCourses(response.data);
        fetchCategoryImages(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching registered courses:", error);
        setError("Failed to load registered courses.");
        setLoading(false);
      });
  };

  const fetchCategoryImages = async (courses) => {
    const newCategoryImages = { ...categoryImages };

    for (const registeredCourse of courses) {
      const category = registeredCourse.course.category;
      if (!newCategoryImages[category]) {
        try {
          const response = await axios.get("https://api.unsplash.com/search/photos", {
            params: { query: category + " programming", per_page: 5 },
            headers: {
              Authorization: `Client-ID BXFizCYQus1Vp_kQm9akpd-F44jDdkeg35K-KNloO-o`,
            },
          });

          newCategoryImages[category] = response.data.results.length
            ? response.data.results.map(img => img.urls.regular)
            : ["https://via.placeholder.com/400x250"];
        } catch (error) {
          console.error(`Error fetching images for ${category}:`, error);
          newCategoryImages[category] = ["https://via.placeholder.com/400x250"];
        }
      }
    }
    setCategoryImages(newCategoryImages);
  };
  const unregisterCourse = async (courseId) => {
    console.log("Received ID:", courseId, typeof courseId);

    if (!courseId || isNaN(Number(courseId))) {
      console.error("Invalid course ID:", courseId);
      alert("Error: Invalid course ID.");
      return;
    }

    if (!window.confirm("Are you sure you want to unregister from this course?")) {
      return;
    }

    try {
      const response = await axios.delete(`http://localhost:8080/registered-courses-details/${courseId}`);

      if (response.status === 200 || response.status === 204) {
        console.log("Successfully unregistered course ID:", courseId);
        // 🔥 Update UI instantly
        setCourses((prevCourses) => prevCourses.filter(course => course.registrationId !== courseId));
      } else {
        console.error("Unexpected response status:", response.status);
        alert("Failed to unregister the course. Unexpected response from the server.");
      }
    } catch (error) {
      console.error("Error unregistering course:", error);

      if (error.response) {
        console.error("Server responded with:", error.response.status, error.response.data);
        alert(`Error: ${error.response.status} - ${error.response.data}`);
      } else {
        alert("Failed to unregister the course. Please check your network and try again.");
      }
    }
  };


  return (
    <div className="container my-4">
      <h1 className="display-5 fw-bold text-center mb-4">Registered Courses</h1>
      {loading && <p className="text-center text-primary">Loading courses...</p>}
      {error && <p className="text-center text-danger">{error}</p>}

      <div className="row gy-4">
        {courses.length > 0 ? (
          courses.map((registeredCourse) => {
            const images = categoryImages[registeredCourse.course.category] || ["https://via.placeholder.com/400x250"];
            const randomImage = images[Math.floor(Math.random() * images.length)];

            return (
              <div key={registeredCourse.id} className="col-md-4 gx-4">
                <div className="card h-100 shadow-lg position-relative bg-dark">
                  <div className="position-relative">
                    <img
                      src={randomImage}
                      className="card-img-top img-fluid"
                      alt={registeredCourse.course.category}
                      style={{ height: "200px", objectFit: "cover" }}
                    />
                    <div
                      className="position-absolute top-0 start-0 w-100 h-100"
                      style={{
                        background: "linear-gradient(to top, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.5))",
                      }}
                    ></div>

                  </div>

                  <div className="card-body d-flex flex-column">
                    <h6 className="text-light">{registeredCourse.course.category}</h6>
                    <p className="card-text flex-grow-1 text-white"> registered by {registeredCourse.user.userName} (ID: {registeredCourse.user.id})</p>
                    <div className="position-absolute top-1 end-0 p-3">
                                       <img
                                         src={`https://api.dicebear.com/7.x/identicon/svg?seed=${registeredCourse.user.userName}`}
                                         alt="User Avatar"
                                         className="d-block mx-auto"
                                         style={{
                                           margin: "0px auto", // Centers horizontally
                                           width: "30px",
                                           height: "30px",
                                           objectFit: "cover",
                                           display: "flex",
                                           alignItems: "center",
                                           justifyContent: "center",
                                         }}
                                       />

                                        </div>
                                      <div className="position-absolute top-0 start-0 p-2">
                                        <i className="bi bi-star-fill text-warning fs-3"></i>
                                      </div>

                    <h3 className="card-title text-light">{registeredCourse.course.courseTitle}</h3>
                   <p className="card-text flex-grow-1 text-white-50">{registeredCourse.course.desc}</p>
  <h5 className="text-success">₹{registeredCourse.course.cost}</h5>
                    <button
                      className="btn btn-outline-light mt-auto w-100"
                      onClick={() => unregisterCourse(registeredCourse.registrationId)}
                    >
                      Unregister
                    </button>
                  </div>
                </div>
              </div>
            );
          })
        ) : (
          !loading && <p className="text-center text-danger">No registered courses.</p>
        )}
      </div>
    </div>
  );
};

export default RegisteredCourseList;
