module.exports = {
    devServer: {
        proxy: {
            '^/api': {
                target: 'http://localhost:65535',
                changeOrigin: true
            },
        }
    }
}