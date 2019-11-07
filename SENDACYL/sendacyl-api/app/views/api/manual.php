<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>SendaCYL API</title>

	<style type="text/css">

	::selection { background-color: #E13300; color: white; }
	::-moz-selection { background-color: #E13300; color: white; }

	body {
		background-color: #fff;
		margin: 40px;
		font: 13px/20px normal Helvetica, Arial, sans-serif;
		color: #4F5155;
	}

	a {
		color: #003399;
		background-color: transparent;
		font-weight: normal;
	}

	h1 {
		color: #444;
		background-color: transparent;
		border-bottom: 1px solid #D0D0D0;
		font-size: 19px;
		font-weight: normal;
		margin: 0 0 14px 0;
		padding: 14px 15px 10px 15px;
	}

	code {
		font-family: Consolas, Monaco, Courier New, Courier, monospace;
		font-size: 12px;
		background-color: #f9f9f9;
		border: 1px solid #D0D0D0;
		color: #002166;
		display: block;
		margin: 14px 0 14px 0;
		padding: 12px 10px 12px 10px;
	}

	#body {
		margin: 0 15px 0 15px;
	}

	p.footer {
		text-align: right;
		font-size: 11px;
		border-top: 1px solid #D0D0D0;
		line-height: 32px;
		padding: 0 10px 0 10px;
		margin: 20px 0 0 0;
	}

	#container {
		margin: 10px;
		border: 1px solid #D0D0D0;
		box-shadow: 0 0 8px #D0D0D0;
	}

	strong {
		font-weight: bolder;
	}

	</style>
</head>
<body>

<div id="container">
	<h1>Manual de uso de la API JSON de SendaCYL</h1>

	<div id="body">

		<h2>Obtener rutas</h2>

		<p>La URL base es, a partir de aquí se harán todas las llamadas:</p>
		<code>http://sendacyl.com/api/</code>

		<p>A continuación se especificará el controlador 'routes' como argumento para traer algunas rutas con el siguiente formato JSON.</p>
		<code>http://sendacyl.com/api<strong>/routes/</strong></code>

		<code>
			<pre>
{
    "status": "ok",
    "count": 1,
    "pages": 10,
    "routes": [
        {
            "id": "2",
            "placemark_id": "senda.fid--111734c8_1510f6351ee_2a9e",
            "atr_gr_id": "89945",
            "atr_gr_tipo": "15",
            "atr_gr_visible": "true",
            "atr_gr_tiene_q": "false",
            "atr_gr_alerta": "false",
            "equip_a_codigo": "SDCYL00031",
            "equip_a_observaciones": "",
            "equip_a_estado_fecha": "2011-06-13 22:00:00",
            "equip_a_fecha_declaracion": "1997-06-16 22:00:00",
            "estado_id": "0",
            "equip_b_senalizacion_ext": "true",
            "equip_b_acceso_modo": "",
            "equip_b_nombre": "GR 99: Puentelarra-Miranda de Ebro",
            "cerramiento_id": "1",
            "acc_id": "0",
            "acc_dis_id": "0",
            "equip_b_tiene_interes": "false",
            "equip_b_superficie_aprox": "0",
            "cerramiento_material_id": "1",
            "web": "",
            "senda_tipo": "0",
            "senda_longitud": "16450",
            "senda_tiempo_recorrido": "0",
            "senda_ciclabilidad": "0",
            "senda_codigo": "GR 99",
            "senda_dificultad": "2",
            "senda_desnivel": "0",
            "tipo_oficial": "2",
            "gmaps_street": "Avenida Rep\u00fablica Argentina",
            "gmaps_locality": "Miranda de Ebro",
            "gmaps_province": "Burgos",
            "gmaps_region": "Castilla y Le\u00f3n",
            "gmaps_country": "Spain",
            "gmaps_lat": "42.6820921",
            "gmaps_lng": "-2.9467084",
            "gmaps_pano_id": "1ahTFihdT5VhE045GF60_Q"
        }
    ]
}
			</pre>
		</code>

		<p>Para especificar el número de rutas a traer añadiremos el parámetro GET 'count'</p>
		<code>http://sendacyl.com/api/routes/<strong>?count=200</strong></code>
		<p>Si por lo contrario queremos un rango de rutas lo pondrémos en el parámetro GET 'count' separado con un pipeline</p>
		<code>http://sendacyl.com/api/routes/<strong>?count=10|20</strong> <br> <small>(esto traerá las rutas desde la 10 a la 20)</small></code>

		<h2>Obtener rutas por Provincia</h2>
		<p>El método 'province' hará esta labor, por ejemplo:</p>
		<code>http://sendacyl.com/api/routes/province/<strong>Soria</strong>?count=0|10 <br> <small>IMPORTANTE: lo que va después de province tiene que estar codificado en UTF-8, en Java para hacer esto podemos usar URLEncoder.encode("Ávila", "UTF-8") entre un try-catch para controlar MalformedURLException.</small></code>

		<h2>Buscar rutas por cadena de texto</h2>
		<p>El método 'search' servirá para buscar en los campos <i>(equip_b_nombre, equip_a_observaciones, equip_b_acceso_modo, gmaps_locality, gmaps_province, gmaps_street)</i>:</p>
		<code>http://sendacyl.com/api/routes/search/<strong>Camino%20de%20Santa%20Coloma</strong>?dev=1 <br> <small>IMPORTANTE: lo que va después de province tiene que estar codificado en UTF-8, en Java para hacer esto podemos usar URLEncoder.encode("Ávila", "UTF-8") entre un try-catch para controlar MalformedURLException.</small></code>

		<h2>Filtrar rutas</h2>
		<p>El método 'filter' servirá para filtrar las rutas, se añadirán condiciones en base a lo que se necesite, de momento solo se puede por dificultad y provincia:</p>
		<code>http://sendacyl.com/api/routes/filter/<strong>?diff=1&province=Soria</strong>?dev=1 <br></code>

		<h2>Obtener ruta por ID</h2>
		<p>Para obtener una ruta por su ID interno la traerémos de esta forma</p>
		<code>http://sendacyl.com/api/routes/view/<strong>34</strong></code>

		<h2>Recursos KML e IMG</h2>
		<p>Los KML de las rutas están ubicados en la carpeta ./public/kml/id_ruta.kml. Por ejemplo, para la ruta con ID 1 sería:</p>
		<code>http://sendacyl.com<strong>/public/kml/1.kml</strong></code>
		<p>Las imágenes siguen el mismo patron anterior, solo que esta vez en la carpeta img y utilizando los 3 primeros caracteres de el campo 'equip_a_codigo'.</p>
		<code>http://sendacyl.com<strong>/public/img/MDL.jpg</strong></code>

		<h2>Modo desarrollador</h2>
		<p>El JSON se imprimirá de forma legible para el navegador (pretty print), solo tenemos que añadir el parametro get 'dev=1' a nuestra consulta</p>
		<code>http://sendacyl.com/api/routes/view/42<strong>?dev=1</strong></code>
	</div>

	<p class="footer">Página renderizada en <strong>{elapsed_time}</strong> segundos.</p>
</div>


</body>
</html>