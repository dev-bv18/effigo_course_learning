import React, { useEffect, useState } from "react";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";

const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8080";

const FavouriteCourseList = () => {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [categoryImages, setCategoryImages] = useState({});

  useEffect(() => {
    fetchFavouriteCourses();
  }, []);

  const fetchFavouriteCourses = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await axios.get(`${API_URL}/favourite-course-details/get-favourite-courses`);
      setCourses(response.data);
      fetchCategoryImages(response.data);
    } catch (error) {
      console.error("Error fetching favourite courses:", error);
      setError("Failed to load favourite courses.");
    } finally {
      setLoading(false);
    }
  };

  const fetchCategoryImages = async (courses) => {
    const newCategoryImages = {};
    await Promise.all(
      courses.map(async (course) => {
        const category = course.registeredCourse.course.category;
        if (!newCategoryImages[category]) {
          try {
            const response = await axios.get("https://api.unsplash.com/search/photos", {
              params: { query: `${category} programming`, per_page: 5 },
              headers: {
                Authorization: `Client-ID BXFizCYQus1Vp_kQm9akpd-F44jDdkeg35K-KNloO-o`,
              },
            });
            newCategoryImages[category] = response.data.results.length
              ? response.data.results.map((img) => img.urls.regular)
              : ["https://via.placeholder.com/400x250"];
          } catch (error) {
            console.error(`Error fetching images for ${category}:`, error);
            newCategoryImages[category] = ["https://via.placeholder.com/400x250"];
          }
        }
      })
    );
    setCategoryImages(newCategoryImages);
  };

  const unregisterCourse = async (favouriteId) => {
    if (!window.confirm("Are you sure you want to unregister from this course?")) return;

    try {
      await axios.delete(`${API_URL}/favourite-course-details/${favouriteId}`);
      setCourses((prevCourses) => prevCourses.filter((course) => course.favouriteId !== favouriteId));
      alert("Successfully unregistered from the course.");
    } catch (error) {
      alert("Error: Could not unregister. Please try again.");
    }
  };

  return (
    <div className="container my-4">
      <h1 className="display-5 fw-bold text-center mb-4">Favourite Courses</h1>
      {loading && <p className="text-center text-primary">Loading courses...</p>}
      {error && <p className="text-center text-danger">{error}</p>}

      <div className="row gy-4">
        {courses.length > 0 ? (
          courses.map((favourite) => {
            const images = categoryImages[favourite.registeredCourse.course.category] || ["https://via.placeholder.com/400x250"];
            const randomImage = images[Math.floor(Math.random() * images.length)];
            return (
              <div key={favourite.favouriteId} className="col-md-4 gx-4">
                <div className="card h-100 shadow-lg position-relative bg-dark">
                  <img
                    src={randomImage}
                    className="card-img-top img-fluid"
                    alt={favourite.registeredCourse.course.category}
                    style={{ height: "200px", objectFit: "cover" }}
                  />
                   <div className="position-absolute top-1 end-0 p-3">
                                                         <img
                                                           src={`https://api.dicebear.com/7.x/identicon/svg?seed=${favourite.registeredCourse.user.userName}`}
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
                  <div className="card-body d-flex flex-column">
                  <div className="d-flex justify-content-between">
                    <h6 className="text-light">{favourite.registeredCourse.course.category}</h6>
                    <h6 className="text-light">{favourite.favouriteId}</h6>
                  </div>
 <p className="text-white">
                      Registered by {favourite.registeredCourse.user.userName} (ID: {favourite.registeredCourse.user.id})
                    </p>
                    <h3 className="card-title text-light">{favourite.registeredCourse.course.courseTitle}</h3>
                    <p className="text-white-50">{favourite.registeredCourse.course.desc}</p>
                    <h5 className="text-success">₹{favourite.registeredCourse.course.cost}</h5>
                    <button className="btn btn-outline-light mt-auto w-100" onClick={() => unregisterCourse(favourite.favouriteId)}>
                      Remove
                    </button>
                  </div>
                </div>
              </div>
            );
          })
        ) : (
          !loading && <p className="text-center text-danger">No favourite courses found.</p>
        )}
      </div>
    </div>
  );
};

export default FavouriteCourseList;