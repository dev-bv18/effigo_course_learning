import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";

const CourseList = () => {
const navigate = useNavigate();
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showAddCourseModal, setShowAddCourseModal] = useState(false);
  const [categoryImages, setCategoryImages] = useState({});
  const [editingCourse, setEditingCourse] = useState(null);
   const [newCourse, setNewCourse] = useState({
      courseTitle: "",
      desc: "",
      cost: "",
      category: "",
    });

  const fetchCourses = () => {
    setLoading(true);
    axios.get("http://localhost:8080/course-details/get-courses")
      .then(response => {
        setCourses(response.data);
        setLoading(false);
        fetchCategoryImages(response.data);
      })
      .catch(error => {
        console.error("Error fetching courses:", error);
        setLoading(false);
      });
  };

  const fetchCategoryImages = async (courses) => {
    const newCategoryImages = { ...categoryImages };

    for (const course of courses) {
      if (!newCategoryImages[course.category]) {
        try {
          const response = await axios.get("https://api.unsplash.com/search/photos", {
            params: { query: course.category + " programming", per_page: 5 },
            headers: {
              Authorization: `Client-ID BXFizCYQus1Vp_kQm9akpd-F44jDdkeg35K-KNloO-o`,
            },
          });

          newCategoryImages[course.category] = response.data.results.length
            ? response.data.results.map(img => img.urls.regular)
            : ["https://via.placeholder.com/400x250"];
        } catch (error) {
          console.error(`Error fetching images for ${course.category}:`, error);
          newCategoryImages[course.category] = ["https://via.placeholder.com/400x250"];
        }
      }
    }
    setCategoryImages(newCategoryImages);
  };

  const deleteCourse = (id) => {
    if (window.confirm("Are you sure you want to delete this course?")) {
      axios.delete(`http://localhost:8080/course-details/${id}`)
        .then(() => {
          setCourses(courses.filter(course => course.id !== id));
        })
        .catch(error => {
          console.error("Error deleting course:", error);
        });
    }
  };

  const handleEditClick = (course) => {
    setEditingCourse(course);
  };

  const handleEditChange = (e) => {
    setEditingCourse({ ...editingCourse, [e.target.name]: e.target.value });
  };

  const saveEditedCourse = () => {
    axios.put("http://localhost:8080/course-details/update-course", editingCourse)
      .then(response => {
        setCourses(courses.map(course => (course.id === response.data.id ? response.data : course)));
        setEditingCourse(null);
      })
      .catch(error => {
        console.error("Error updating course:", error);
      });
  };
   const handleAddCourseChange = (e) => {
      setNewCourse({ ...newCourse, [e.target.name]: e.target.value });
    };
    const addNewCourse = () => {
      axios
        .post("http://localhost:8080/course-details/create-course", newCourse)
        .then((response) => {
          setCourses([...courses, response.data]);
          setShowAddCourseModal(false);
          setNewCourse({ courseTitle: "", desc: "", cost: "", category: "" });
        })
        .catch((error) => {
          console.error("Error adding course:", error);
        });
    };


  useEffect(() => {
    fetchCourses();
  }, []);

  return (
    <div className="container my-4"><button
                                      className="btn btn-outline-dark rounded-circle position-fixed"
                                      style={{
                                        bottom: "20px",
                                        right: "20px",
                                        width: "60px",
                                        height: "60px",
                                        fontSize: "24px",
                                        display: "flex",
                                        justifyContent: "center",
                                        alignItems: "center",
                                        boxShadow: "0px 4px 10px rgba(0,0,0,0.2)",
                                        borderWidth: "2px",
                                      }}
                                      onClick={() => setShowAddCourseModal(true)}
                                    >
                                      <i className="bi bi-plus"></i>
                                    </button>


      <h1 className="display-5 fw-bold text-center mb-4">Available Courses</h1>
      {loading && <p className="text-center text-primary">Loading courses...</p>}
      <div className="row gy-4">
        {courses.length > 0 ? (
          courses.map((course) => {
            const images = categoryImages[course.category] || ["https://via.placeholder.com/400x250"];
            const randomImage = images[Math.floor(Math.random() * images.length)];

            return (
              <div key={course.id} className="col-md-4 gy-2 gx-3">
                <div className="card h-100 shadow-lg position-relative">
                 <div className="position-relative">
                   <img
                     src={randomImage}
                     className="card-img-top img-fluid"
                     alt={course.category}
                     style={{ height: "200px", objectFit: "cover" }}
                   />
                   <div
                     className="position-absolute top-0 start-0 w-100 h-100"
                     style={{
                       background: "linear-gradient(to top, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.7))",
                     }}
                   ></div>

                   {/* Icons Positioned Over the Image */}
                   <div className="position-absolute top-0 end-0 p-2 d-flex">
                     <i
                       className="bi bi-pencil-square text-light me-2"
                       role="button"
                       style={{ fontSize: "1.2rem", cursor: "pointer" }}
                       onClick={() => handleEditClick(course)}
                     ></i>
                     <i
                       className="bi bi-trash text-light"
                       role="button"
                       style={{ fontSize: "1.2rem", cursor: "pointer" }}
                       onClick={() => deleteCourse(course.id)}
                     ></i>
                   </div>
                 </div>


                  <div className="card-body d-flex flex-column">
                    <h6 className="text-muted">{course.category}</h6>
                    <h3 className="card-title text-dark">{course.courseTitle}</h3>
                    <p className="card-text flex-grow-1">{course.desc}</p>
                    <h5 className="text-danger">
                      <strike>₹{(course.cost + 0.5 * course.cost).toFixed(2)}</strike> ₹{course.cost}
                    </h5>
                    <button
                        className="btn btn-dark mt-auto w-100"
                        onClick={() => navigate("/register")}
                      >
                        Enroll Now
                      </button>
                  </div>
                  </div>
              </div>
            );
          })
        ) : (
          !loading && <p className="text-center text-danger">No courses available.</p>
        )}
      </div>
      {editingCourse && (
        <div className="modal fade show d-block" style={{ backgroundColor: "rgba(0,0,0,0.5)" }}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Edit Course</h5>
                <button className="btn-close" onClick={() => setEditingCourse(null)}></button>
              </div>
              <div className="modal-body">
                <input type="text" name="courseTitle" value={editingCourse.courseTitle} onChange={handleEditChange} className="form-control mb-2" placeholder="Title" />
                <input type="text" name="desc" value={editingCourse.desc} onChange={handleEditChange} className="form-control mb-2" placeholder="Description" />
                <input type="number" name="cost" value={editingCourse.cost} onChange={handleEditChange} className="form-control mb-2" placeholder="Cost" />
               <select
                               name="category"
                               value={editingCourse.category}
                               onChange={handleEditChange}
                               className="form-control mb-2"
                             >
                               <option value="">Select Category</option>
                               <option value="AWS">AWS</option>
                               <option value="JAVA">Java</option>
                               <option value="REACT">React</option>
                               <option value="C">C</option>
                             </select></div>
              <div className="modal-footer">
                <button className="btn btn-secondary" onClick={() => setEditingCourse(null)}>Cancel</button>
                <button className="btn btn-primary" onClick={saveEditedCourse}>Save Changes</button>
              </div>
            </div>
          </div>
        </div>
      )}
      {showAddCourseModal && (
        <div className="modal fade show d-block" style={{ backgroundColor: "rgba(0,0,0,0.5)" }}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Add New Course</h5>
                <button className="btn-close" onClick={() => setShowAddCourseModal(false)}></button>
              </div>
              <div className="modal-body">
                <input type="text" name="courseTitle" value={newCourse.courseTitle} onChange={handleAddCourseChange} className="form-control mb-2" placeholder="Title" />
                <input type="text" name="desc" value={newCourse.desc} onChange={handleAddCourseChange} className="form-control mb-2" placeholder="Description" />
                <input type="number" name="cost" value={newCourse.cost} onChange={handleAddCourseChange} className="form-control mb-2" placeholder="Cost" />
              <select
                name="category"
                value={newCourse.category}
                onChange={handleAddCourseChange}
                className="form-control mb-2"
              >
                <option value="">Select Category</option>
                <option value="AWS">AWS</option>
                <option value="JAVA">Java</option>
                <option value="REACT">React</option>
                <option value="C">C</option>
              </select>
</div>
              <div className="modal-footer">
                <button className="btn btn-secondary" onClick={() => setShowAddCourseModal(false)}>Cancel</button>
                <button className="btn btn-success" onClick={addNewCourse}>Add Course</button>
              </div>
            </div>
          </div>
        </div>
      )}

    </div>

  );
};

export default CourseList;