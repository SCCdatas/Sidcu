const API_BASE_URL = (
    import.meta.env.VITE_API_URL || "https://sidcu-2.onrender.com"
).replace(/\/+$/, "");

export async function getApiData(path) {
    let response;

    try {
        response = await fetch(`${API_BASE_URL}${path}`);
    } catch (error) {
        if (error instanceof TypeError) {
            throw new Error(
                "No fue posible conectar con el backend. Verifica que esté activo y que permita solicitudes desde este sitio.",
                { cause: error }
            );
        }

        throw error;
    }

    if (!response.ok) {
        throw new Error(`El backend respondió con el estado ${response.status}.`);
    }

    return response.json();
}
