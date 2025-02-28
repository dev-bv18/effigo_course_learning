import { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

const Banner = () => {
  const [isVisible, setIsVisible] = useState(false);

 useEffect(() => {
   const fetchBannerStatus = async () => {
     try {
       const response = await fetch("http://localhost:8080/banner/status");
       const data = await response.json();

       setIsVisible(prevState => {
         if (prevState !== data) {
           return data;
         }
         return prevState;
       });
     } catch (error) {
       console.error("Error fetching banner status:", error);
     }
   };

// fetchBannerStatus();
//   const interval = setInterval(fetchBannerStatus, 5000);
//
//   return () => clearInterval(interval);
 }, []);

  return isVisible ? (
    <div className="container mt-3">
      <div
        className="position-relative text-center text-white py-5"
        style={{
          backgroundImage: "url('https://static.vecteezy.com/system/resources/thumbnails/007/808/325/small/flash-sale-banner-template-design-for-web-or-social-media-vector.jpg')",
          backgroundSize: "cover",
          width:"100%",
          margin:"0px",
          backgroundPosition: "center",
          borderRadius: "10px",
        }}
      >
        <div className="bg-dark bg-opacity-50 p-4 rounded">
          <h2 className="fw-bold">🎉 Special Announcement! 🎉</h2>
          <p className="lead">Don't miss out on our latest offers and updates!</p>
        </div>
      </div>
    </div>
  ) : null;
};

export default Banner;
