const jwt = require('jsonwebtoken');

class CheckToken {
    check() {
        return (req, res, next) => {
            const token = req.headers['x-access-token'];
            if (!token) {
                return res.status(401).send({auth: false, token: 'No token provided.'});
            }

            if (token) {
                jwt.verify(token, process.env.SECRET, (err, decodes) => {
                    if (err) {
                        return res.json({
                            success: false,
                            message: 'Token is not valid'
                        });
                    } else {
                        next();
                    }
                });
            } else {
                return res.json({
                    success: false,
                    message: 'Auth token is not supplied'
                });
            }
        }
    }
}


const checkToken = (req, res, next) => {
    if (req) {
        const token = req.headers['x-access-token'];
        if (!token) {
            return res.status(401).send({auth: false, token: 'No token provided.'});
        }

        if (token) {
            jwt.verify(token, process.env.SECRET, (err, decodes) => {
                if (err) {
                    return res.json({
                        success: false,
                        message: 'Token is not valid'
                    });
                } else {
                    next();
                }
            });
        } else {
            return res.json({
                success: false,
                message: 'Auth token is not supplied'
            });
        }
    }
};

module.exports = new CheckToken();
