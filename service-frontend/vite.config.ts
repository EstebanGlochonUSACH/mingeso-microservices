import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

const BACKEND_URI = process.env.BACKEND_URI ? process.env.BACKEND_URI : 'http://localhost:8080';
const SERVICES_AUTOS_URI = process.env.SERVICES_AUTOS_URI ? process.env.SERVICES_AUTOS_URI : 'http://localhost:8090';
const SERVICES_REPARACIONES_URI = process.env.SERVICES_REPARACIONES_URI ? process.env.SERVICES_REPARACIONES_URI : 'http://localhost:8091';
const SERVICES_ORDENES_URI = process.env.SERVICES_ORDENES_URI ? process.env.SERVICES_ORDENES_URI : 'http://localhost:8092';
const SERVICES_REPORTES_URI = process.env.SERVICES_REPORTES_URI ? process.env.SERVICES_REPORTES_URI : 'http://localhost:8093';

// https://vitejs.dev/config/
export default defineConfig({
	server: {
		host: '0.0.0.0',
		port: 3000,
		strictPort: true,
		proxy: {
			// '/api': {
			//	target: BACKEND_URI,
			//	changeOrigin: true,
			//	rewrite: (path) => path.replace(/^\/api\//, '/'),
			// },
			'/api/autos': {
				target: SERVICES_AUTOS_URI,
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/api\//, '/'),
			},
			'/api/marcas': {
				target: SERVICES_AUTOS_URI,
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/api\//, '/'),
			},
			'/api/reparaciones': {
				target: SERVICES_REPARACIONES_URI,
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/api\//, '/'),
			},
			'/api/ordenes': {
				target: SERVICES_ORDENES_URI,
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/api\//, '/'),
			},
			'/api/bonos': {
				target: SERVICES_ORDENES_URI,
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/api\//, '/'),
			},
			'/api/reportes': {
				target: SERVICES_REPORTES_URI,
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/api\//, '/'),
			},
		}
	},
	plugins: [react()],
})
