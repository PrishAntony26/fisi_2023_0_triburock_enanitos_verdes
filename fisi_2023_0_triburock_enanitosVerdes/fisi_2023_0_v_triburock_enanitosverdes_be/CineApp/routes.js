const express = require('express');
const routes = express.Router();
const multer = require('multer');
const path = require("path");


routes.get('/peliculas', (req, res)=>{
    req.getConnection((err, conn)=>{
        if(err) return res.send(err);

        conn.query('SELECT * FROM pelicula', (err, rows)=>{
         if(err) return res.send(err);
         var rowsOriginal = new Array();
         for (let i = 0; i < 7; i++) {
            rowsOriginal[i] = rows[i]; 
         }
         console.log("---------------------");
         res.json(rowsOriginal);
        });
    })
})

routes.get('/buscarcine/:nombre/:distrito', (req, res)=>{
    req.getConnection((err, conn)=>{
        if(err) return res.send(err);

        conn.query('select c.Nombre_cine , c.coordenadaX, c.coordenadaY ,p.nombre , p.Formato_pelicula, c.Direccion_cine , f.Precio_nino, f.Precio_adulto, f.Precio_Amayor, f.Horario from cinefilo.funcion f inner join cinefilo.pelicula p on f.Id_pelicula  = p.Id_pelicula inner join cinefilo.cine c on f.Id_cine  = c.idCine  where p.nombre = ? and c.Distrito_cine = ?',[[req.params.nombre], [req.params.distrito]], (err, rows)=> {
            if(err) return res.send(err);
            console.log("---------------------");
            res.json(rows);
        })
    })
})


module.exports = routes;