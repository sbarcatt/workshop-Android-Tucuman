# Cambios

- Agregado GET a ```https://workshop-go-utnfrt.herokuapp.com/purchases``` en HomeActivity para consultar las compras.
---
- Agregado GET a ```https://workshop-go-utnfrt.herokuapp.com/purchases/:id``` en DetailsActivity para consultar la compra elegida en HomeActivity pasando como parametro el ID de purchases.
---
- Agregado algunos campos en el BaseAdapater (id, title, amount, Fecha y hora, status).
---
- Agregado ImageView para mostrar la foto en el ListView.
---
- Cargar descripción de cada venta en base al ID seleccionado del ListView.
---
- Usando AsyncTask para descargar las imágenes en segundo plano.




## Dependencias
Volley para las query a ```https://workshop-go-utnfrt.herokuapp.com/purchases```
- [Volley](https://developer.android.com/training/volley).



