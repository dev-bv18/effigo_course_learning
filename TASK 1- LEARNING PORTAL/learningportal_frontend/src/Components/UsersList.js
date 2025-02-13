import axios from "axios";
import { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";

const UsersList = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showAddUserModal, setShowAddUserModal] = useState(false);
  const [showEdUserModal,setShowEdUserModal]=useState(false);
  const [editingUser, setEditingUser] = useState(null);
  const [newUser, setNewUser] = useState({
    userName: "",
    passWord: "",
    userRole: "",
  });

  const API_BASE = "http://localhost:8080/users-details";

  // Fetch Users
  const fetchUsers = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`${API_BASE}/get-users`);
      setUsers(response.data);
    } catch (error) {
      console.error("Error fetching users:", error);
      alert("Error fetching users. Please try again.");
    }
    setLoading(false);
  };
const handleEditChange = (e) => {
  setEditingUser({ ...editingUser, [e.target.name]: e.target.value});
};
  // Delete User
  const deleteUser = async (id) => {
    if (!window.confirm("Are you sure you want to delete this user?")) return;
    try {
      await axios.delete(`${API_BASE}/${id}`);
      setUsers(users.filter(user => user.id !== id));
    } catch (error) {
      console.error("Error deleting user:", error);
      alert("Error deleting user. Please try again.");
    }
  };

const handleEditClick = (user) => {
  setEditingUser({ ...user });
  setShowEdUserModal(true);
};

const saveEditedUser = async () => {
  try {
    const response = await axios.put(`${API_BASE}/update-users`, editingUser, {
      headers: { "Content-Type": "application/json" },
    });

    setUsers(users.map(user => (user.id === response.data.id ? response.data : user)));

    setShowEdUserModal(false); // Close modal after save
    setEditingUser(null); // Reset editing state
  } catch (error) {
    console.error("Error updating user:", error);
    alert("Error updating user. Please try again.");
  }
};


  const handleAddUserChange = (e) => {
    setNewUser({ ...newUser, [e.target.name]: e.target.value });
  };

  // Add New User
  const addNewUser = async () => {
    if (!newUser.userName || !newUser.passWord || !newUser.userRole) {
      alert("All fields are required!");
      return;
    }

    try {
      const response = await axios.post(`${API_BASE}/create-users`, newUser, {
        headers: { "Content-Type": "application/json" },
      });

      setUsers([...users, response.data]);
      setShowAddUserModal(false);
      setNewUser({ userName: "", passWord: "", userRole: "" });
    } catch (error) {
      console.error("Error adding user:", error.response ? error.response.data : error);
      alert("Error adding user. Please check the console.");
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    <div className="container my-4">
      <button
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
        onClick={() => setShowAddUserModal(true)}
      >
        <i className="bi bi-plus"></i>
      </button>

      <h1 className="display-5 fw-bold text-center mb-4">Available Users</h1>
      {loading && <p className="text-center text-primary">Loading users...</p>}
      <div className="row gy-4">
        {users.length > 0 ? (
          users.map((user) => (
            <div key={user.id} className="col-md-4 gy-4 gx-5">
              <div className="card h-100 shadow-lg position-relative">
                <div className="position-relative">
                  <img
                    src={`https://api.dicebear.com/7.x/identicon/svg?seed=${user.userName}`}
                    alt="User Avatar"
                    className="mx-auto d-block"
                    style={{ margin: "20px", width: "100px", height: "100px", objectFit: "cover" }}
                  />

                  {/* Icons Positioned Over the Image */}
                  <div className="position-absolute top-0 end-0 p-2 d-flex">
                    <i
                      className="bi bi-pencil-square text-primary me-2"
                      role="button"
                      style={{ fontSize: "1.2rem", cursor: "pointer" }}
                      onClick={() => handleEditClick(user)}
                    ></i>
                    <i
                      className="bi bi-trash text-danger"
                      role="button"
                      style={{ fontSize: "1.2rem", cursor: "pointer" }}
                      onClick={() => deleteUser(user.id)}
                    ></i>
                  </div>
                </div>

                <div className="card-body d-flex flex-column">
                  <h6 className="text-muted text-xs">{user.id}</h6>
                  <h3 className="card-title text-dark">{user.userName}</h3>
                  <h6 className="text-muted">Role: {user.userRole}</h6>
                </div>
              </div>
            </div>
          ))
        ) : (
          !loading && <p className="text-center text-danger">No users available.</p>
        )}
      </div>
{/* Edit User Modal */}
{showEdUserModal && editingUser && (
  <div className="modal fade show d-block" style={{ backgroundColor: "rgba(0,0,0,0.5)" }}>
    <div className="modal-dialog">
      <div className="modal-content">
        <div className="modal-header">
          <h5 className="modal-title">Edit User</h5>
          <button className="btn-close" onClick={() => setShowEdUserModal(false)}></button>
        </div>
        <div className="modal-body">
          <input
            type="text"
            name="userName"
            value={editingUser.userName}
            onChange={handleEditChange}
            className="form-control mb-2"
            placeholder="Username"
          />
          <select
            name="userRole"
            value={editingUser.userRole}
            onChange={handleEditChange}
            className="form-control mb-2"
          >
            <option value="">Select Role</option>
            <option value="ADMIN">ADMIN</option>
            <option value="AUTHOR">AUTHOR</option>
            <option value="LEARNER">LEARNER</option>
          </select>
        </div>
        <div className="modal-footer">
          <button className="btn btn-secondary" onClick={() => setShowEdUserModal(false)}>Cancel</button>
          <button className="btn btn-primary" onClick={saveEditedUser}>Save Changes</button>
        </div>
      </div>
    </div>
  </div>
)}

      {/* Add User Modal */}
      {showAddUserModal && (
        <div className="modal fade show d-block" style={{ backgroundColor: "rgba(0,0,0,0.5)" }}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Add New User</h5>
                <button className="btn-close" onClick={() => setShowAddUserModal(false)}></button>
              </div>
              <div className="modal-body">
                <input type="text" name="userName" value={newUser.userName} onChange={handleAddUserChange} className="form-control mb-2" placeholder="Username" />
                <input type="password" name="passWord" value={newUser.passWord} onChange={handleAddUserChange} className="form-control mb-2" placeholder="Password" />
                  <select name="userRole"  value={newUser.userRole}
                                               onChange={handleAddUserChange}
                                               className="form-control mb-2"
                                             >
                                               <option value="">Select Role</option>
                                               <option value="ADMIN">ADMIN</option>
                                               <option value="AUTHOR">AUTHOR</option>
                                               <option value="LEARNER">LEARNER</option>
                                             </select>
                   </div>
              <div className="modal-footer">
                <button className="btn btn-secondary" onClick={() => setShowAddUserModal(false)}>Cancel</button>
                <button className="btn btn-primary" onClick={addNewUser}>Add User</button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default UsersList;
