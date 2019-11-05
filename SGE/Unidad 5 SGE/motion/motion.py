from osv import fields, osv 
 
import time 
 
 
 
class motion_pelicula(osv.osv):    
 
        _name = 'motion.pelicula' 
 
        _description = 'Pelicula' 
 
        _columns = {                
 
        'nombre_d' : fields.char('Nombre director', size=30, required=True), 
 
        'nombre' : fields.char('Nombre pelicula', size=30, required=True),        
 
        'fecha' : fields.date('Fecha de adquisicion', required=True), 
 
        'costo_a' : fields.integer('Costo alquiler'), 
 
        'costo' : fields.integer('Costo de la pelicula'), 
 
        'codigo' : fields.integer('Codigo', required=True), 
 
        'numero' : fields.integer('Numero de copias', required=True),  
 
        'notas' : fields.text('Notas',required=True),
 
    } 
 
        _defaults = { 
 
        'fecha' : lambda *a : time.strftime("%Y-%m-%d"),        
 
    } 
 
motion_pelicula() 
