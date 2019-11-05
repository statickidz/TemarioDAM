# -*- coding: utf-8 -*-
import sys, os

print "API:", sys.api_version
print "Encode:", sys.getdefaultencoding()
print "Tamaño de Hola:", sys.getsizeof("Hola", 0)
print "Path:", sys.path
print "Plataforma:", sys.platform
print "Versión:", sys.version
print sys.modules
print"	Módulo os gestión archivos y directorios	"
print "Directorio actual:", os.getcwd()
print "Path:", os.defpath
print "Variables de entorno:", os.environ
print "Pid:", os.getpid()

#	access, chdir, mkdir, open, remove, read, rename, chmod, dup, dup2, exec	, kill, umask, ulink
for fil in os.listdir(os.getcwd()):
    print "Nombre:", fil

for path, dirs, files in os.walk(os.getcwd()): # Para cada directorio de forma recursiva ver como entra en srdOPck forfil in files: #recorremos todos los ficheros encontrados filename = os.path.join(path, fil)
    for fil in files:
        filename = os.path.join(path, fil)
        print "Tamaño de " + filename + " es ", os.path.getsize(filename)

# os.path:
path = os.getcwd()
print"	Objeto os.path	"
print "path absoluto:", os.path.abspath(path)
print "basename:", os.path.basename(path)
print "dirname:", os.path.dirname(filename)
print "tiempo de acceso:", os.path.getatime(path)
print "es un directorio:", os.path.isdir(path)  # isfile,islink,ismount
print "la unidad es:", os.path.splitdrive(path)[0]
sys.exit()
