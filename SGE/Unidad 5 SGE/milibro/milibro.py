# -*- encoding: utf-8 -*-
from osv import osv,fields 

class milibro(osv.osv):
    _name = 'milibro' 
    _columns = {        
    'titulo': fields.char('Título', size=100,required=True),
    'paginas': fields.integer('Páginas', required=False), 
    'autor': fields.char('Autor', size=100, required=True), 
    'editorial': fields.char('Editorial', size=100,required=False),
    }   
milibro()

