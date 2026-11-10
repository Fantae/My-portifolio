import { useState, useEffect } from 'react'
import './App.css'

// Your live backend URL
const API_BASE_URL = "https://my-portfolio-a4936.containers.snapdeploy.com"; 

function App() {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fetch data from your Spring Boot backend
    fetch(`${API_BASE_URL}/api/projects`)
      .then((response) => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then((data) => {
        setProjects(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching projects:", err);
        setError("Failed to load projects. Make sure the backend is awake and CORS is configured.");
        setLoading(false);
      });
  }, []);

  return (
    <div className="portfolio-container">
      <header className="hero-section">
        <h1>Fanta Eba Bededa</h1>
        <h2>Full-Stack AI Developer | Software Engineer</h2>
        <p>
          Welcome to my portfolio. Below are some of the projects I have built 
          using Spring Boot, React, and AI technologies.
        </p>
      </header>

      <section className="projects-section">
        <h3>My Projects</h3>
        
        {loading && <p className="status-text">Loading projects from backend... (If it's asleep, this might take 30 seconds)</p>}
        
        {error && <p className="error-text">{error}</p>}

        <div className="projects-grid">
          {projects.map((project) => (
            <div key={project.id} className="project-card">
              <h4>{project.title || project.name || "Untitled Project"}</h4>
              <p>{project.description || "No description provided."}</p>
              {project.link && (
                <a href={project.link} target="_blank" rel="noopener noreferrer">
                  View Project
                </a>
              )}
            </div>
          ))}
        </div>
        
        {!loading && !error && projects.length === 0 && (
          <p className="status-text">No projects found in the database yet.</p>
        )}
      </section>
    </div>
  )
}

export default App