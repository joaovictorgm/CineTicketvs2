'use client';

import { useRouter } from 'next/navigation';
import React from 'react';

export default function Home() {

  const router = useRouter();

  return (
    <>
      <style>{`
        :root {
          --primary-blue: #0284c7;
          --dark-blue: #0f172a;
          --accent-blue: #38bdf8;
          --bg-color: #0b0f19;
          --text-light: #f8fafc;
          --text-gray: #94a3b8;
          --transition: all 0.3s ease;
        }

        * {
          margin: 0;
          padding: 0;
          box-sizing: border-box;
          font-family: 'Poppins', sans-serif;
          scroll-behavior: smooth;
        }

        body {
          background-color: var(--bg-color);
          color: var(--text-light);
          line-height: 1.6;
        }

        header {
          position: fixed;
          top: 0;
          left: 0;
          width: 100%;
          background: rgba(15, 23, 42, 0.9);
          backdrop-filter: blur(10px);
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 1.2rem 5%;
          z-index: 1000;
          border-bottom: 1px solid rgba(56, 189, 248, 0.1);
        }

        .logo-container {
          display: flex;
          align-items: center;
          gap: 12px;
          cursor: pointer;
        }

        .logo-icon {
          font-size: 1.5rem;
          color: var(--accent-blue);
          background: rgba(2, 132, 199, 0.2);
          padding: 8px 12px;
          border-radius: 12px;
        }

        .logo-text {
          font-size: 1.5rem;
          font-weight: 700;
          letter-spacing: 0.5px;
          color: #f8fafc;
        }

        .logo-text span {
          color: var(--accent-blue);
        }

        nav ul {
          display: flex;
          list-style: none;
          gap: 2rem;
          align-items: center;
        }

        nav a {
          text-decoration: none;
          color: var(--text-light);
          font-weight: 400;
          transition: var(--transition);
        }

        nav a:hover {
          color: var(--accent-blue);
        }

        .btn-login {
          background-color: transparent;
          border: 2px solid var(--primary-blue);
          color: var(--text-light);
          padding: 0.6rem 1.5rem;
          border-radius: 50px;
          font-weight: 600;
          cursor: pointer;
          transition: var(--transition);
          display: flex;
          align-items: center;
          gap: 8px;
        }

        .btn-login:hover {
          background-color: var(--primary-blue);
          box-shadow: 0 0 15px rgba(2, 132, 199, 0.4);
        }

        .hero {
          height: 100vh;
          display: flex;
          align-items: center;
          justify-content: space-between;
          padding: 0 5%;
          background: radial-gradient(circle at 70% 30%, rgba(2, 132, 199, 0.15) 0%, transparent 60%);
          padding-top: 80px;
        }

        .hero-content {
          max-width: 600px;
        }

        .hero-content h1 {
          font-size: 3.5rem;
          font-weight: 700;
          line-height: 1.2;
          margin-bottom: 1.5rem;
        }

        .hero-content h1 span {
          color: var(--accent-blue);
        }

        .hero-content p {
          color: var(--text-gray);
          font-size: 1.1rem;
          margin-bottom: 2rem;
        }

        .btn-primary {
          background: linear-gradient(135deg, var(--primary-blue), var(--accent-blue));
          color: white;
          border: none;
          padding: 0.9rem 2.2rem;
          font-size: 1rem;
          font-weight: 600;
          border-radius: 50px;
          cursor: pointer;
          transition: var(--transition);
          text-decoration: none;
          box-shadow: 0 4px 20px rgba(56, 189, 248, 0.3);
          display: inline-block;
        }

        .btn-primary:hover {
          transform: translateY(-2px);
          box-shadow: 0 6px 25px rgba(56, 189, 248, 0.5);
        }

        .hero-banner {
          font-size: 15rem;
          color: rgba(56, 189, 248, 0.05);
        }

        .story {
          padding: 8rem 5%;
          background-color: #0f172a;
          text-align: center;
        }

        .story h2 {
          font-size: 2.5rem;
          margin-bottom: 1.5rem;
        }

        .story h2 span {
          color: var(--accent-blue);
        }

        .story-container {
          max-width: 800px;
          margin: 0 auto;
          background: rgba(2, 132, 199, 0.05);
          border: 1px solid rgba(56, 189, 248, 0.1);
          padding: 3rem;
          border-radius: 20px;
          backdrop-filter: blur(5px);
        }

        .story-container p {
          color: var(--text-gray);
          font-size: 1.15rem;
          line-height: 1.8;
        }

        .movies {
          padding: 6rem 5%;
        }

        .section-title {
          text-align: center;
          font-size: 2.5rem;
          margin-bottom: 3rem;
        }

        .section-title span {
          color: var(--accent-blue);
        }

        .movie-grid {
          display: grid;
          grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
          gap: 2rem;
        }

        .movie-card {
          background: #1e293b;
          border-radius: 15px;
          overflow: hidden;
          border: 1px solid rgba(255, 255, 255, 0.05);
          transition: var(--transition);
        }

        .movie-card:hover {
          transform: translateY(-8px);
          border-color: var(--accent-blue);
          box-shadow: 0 10px 30px rgba(2, 132, 199, 0.2);
        }

        .movie-poster {
          height: 250px;
          background: linear-gradient(45deg, #0f172a, var(--primary-blue));
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 3rem;
          color: rgba(255,255,255,0.4);
        }

        .movie-info {
          padding: 1.5rem;
        }

        .movie-info h3 {
          font-size: 1.2rem;
          margin-bottom: 0.5rem;
        }

        .movie-info p {
          color: var(--text-gray);
          font-size: 0.9rem;
          margin-bottom: 1.2rem;
        }

        .btn-buy {
          display: block;
          width: 100%;
          padding: 0.7rem;
          background: var(--primary-blue);
          color: white;
          text-align: center;
          border-radius: 8px;
          text-decoration: none;
          font-weight: 600;
          transition: var(--transition);
        }

        .btn-buy:hover {
          background: var(--accent-blue);
          color: var(--dark-blue);
        }

        footer {
          text-align: center;
          padding: 3rem 5%;
          background: #070a12;
          color: var(--text-gray);
          border-top: 1px solid rgba(255, 255, 255, 0.05);
        }

        @media (max-width: 768px) {
          .hero {
            flex-direction: column;
            justify-content: center;
            text-align: center;
            gap: 2rem;
          }
          .hero-content h1 {
            font-size: 2.5rem;
          }
          .hero-banner {
            display: none;
          }
          nav ul {
            display: none;
          }
        }
      `}</style>

      {/* Header */}
      <header>
        <div className="logo-container">
          <div className="logo-icon" title="Logo: Balde de Pipoca + Óculos 3D">
            🍿 👓
          </div>
          <div className="logo-text">Cine<span>Ticket</span></div>
        </div>
        <nav>
          <ul>
            <li><a href="#home">Início</a></li>
            <li><a href="#historia">Nossa História</a></li>
            <li><a href="#filmes">Filmes</a></li>
          </ul>
        </nav>
        <button className="btn-login" onClick={() => router.push('/login')}>
          👤 Login
        </button>
      </header>

      {/* Hero Section */}
      <section id="home" className="hero">
        <div className="hero-content">
          <h1>A sua experiência de cinema começa <span>aqui.</span></h1>
          <p>Compre ingressos para os melhores filmes em cartaz de forma rápida, segura e sem pegar filas.</p>
          <a href="#filmes" className="btn-primary">Ver Filmes em Cartaz</a>
        </div>
        <div className="hero-banner">
          🎬
        </div>
      </section>

      {/* Nossa História Section */}
      <section id="historia" className="story">
        <h2>Nossa <span>História</span></h2>
        <div className="story-container">
          <p>
            O <strong>CineTicket</strong> é um projeto de venda de ingressos para cinema onde tem os filmes e os clientes podem comprar ingressos dos respectivos filmes. Nascemos para simplificar o seu acesso à sétima arte, garantindo que você descubra os lançamentos e garanta o seu lugar na sala de cinema de forma totalmente descomplicada.
          </p>
        </div>
      </section>

      {/* Filmes Section */}
      <section id="filmes" className="movies">
        <h2 className="section-title">Filmes em <span>Destaque</span></h2>
        <div className="movie-grid">
          <div className="movie-card">
            <div className="movie-poster">🚀</div>
            <div className="movie-info">
              <h3>Aventura no Espaço</h3>
              <p>Ficção Científica • 2h 15m</p>
              <a href="#comprar" className="btn-buy" onClick={(e) => { e.preventDefault(); alert('Redirecionando para compra...'); }}>Comprar Ingresso</a>
            </div>
          </div>
          <div className="movie-card">
            <div className="movie-poster">🕵️‍♂️</div>
            <div className="movie-info">
              <h3>Mistério na Noite</h3>
              <p>Suspense • 1h 50m</p>
              <a href="#comprar" className="btn-buy" onClick={(e) => { e.preventDefault(); alert('Redirecionando para compra...'); }}>Comprar Ingresso</a>
            </div>
          </div>
          <div className="movie-card">
            <div className="movie-poster">😂</div>
            <div className="movie-info">
              <h3>Rindo à Toa</h3>
              <p>Comédia • 1h 35m</p>
              <a href="#comprar" className="btn-buy" onClick={(e) => { e.preventDefault(); alert('Redirecionando para compra...'); }}>Comprar Ingresso</a>
            </div>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer>
        <p>&copy; 2026 CineTicket. Todos os direitos reservados.</p>
      </footer>
    </>
  );
}