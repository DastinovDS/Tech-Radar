import { useState, type FormEvent } from 'react';
import { Search, ExternalLink } from 'lucide-react';

interface RepositoryData {
  id: number;
  name: string;
}

const HomePage = () => {
  const [url, setUrl] = useState<string>('');
  const [repoData, setRepoData] = useState<RepositoryData | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');

  const handleSearch = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    setRepoData(null);

    try {
      const response = await fetch('/api/v1/public/gatherinfo', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ url: url }),
      });

      if (!response.ok) {
        throw new Error('Repository not found or invalid URL');
      }

      const data: RepositoryData = await response.json();
      setRepoData(data);
    } catch (err) {
      const errorMessage = err instanceof Error ? err.message : 'An unexpected error occurred';
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
      <div className="min-h-screen bg-slate-900 text-white flex flex-col items-center justify-center p-6 font-sans">
        {/* Header */}
        <div className="mb-12 text-center">
          <div className="flex justify-center mb-4">
            <div className="text-6xl mb-4">🐙</div>
          </div>
          <h1 className="text-4xl font-bold mb-2 bg-gradient-to-r from-indigo-400 to-cyan-400 bg-clip-text text-transparent">
            GitHub Radar
          </h1>
          <p className="text-slate-400">Enter a GitHub URL to preview repository data</p>
        </div>

        {/* Search Form */}
        <form onSubmit={handleSearch} className="w-full max-w-2xl flex gap-2">
          <div className="relative flex-1">
            <div className="absolute inset-y-0 left-3 flex items-center pointer-events-none">
              <Search size={20} className="text-slate-500" />
            </div>
            <input
                type="text"
                className="w-full bg-slate-800 border border-slate-700 rounded-lg py-3 pl-10 pr-4 focus:outline-none focus:ring-2 focus:ring-indigo-500 transition-all text-slate-200"
                placeholder="https://github.com/owner/repo"
                value={url}
                onChange={(e) => setUrl(e.target.value)}
                required
            />
          </div>
          <button
              type="submit"
              disabled={loading}
              className="bg-indigo-600 hover:bg-indigo-500 disabled:bg-indigo-800 px-6 py-3 rounded-lg font-medium transition-colors flex items-center gap-2 min-w-[120px] justify-center"
          >
            {loading ? (
                <span className="flex items-center gap-2">
              <span className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin" />
              Scanning
            </span>
            ) : (
                'Scan'
            )}
          </button>
        </form>

        {/* Error Message */}
        {error && (
            <div className="mt-4 text-red-400 text-sm bg-red-400/10 px-4 py-2 rounded-md border border-red-400/20 animate-in fade-in zoom-in duration-300">
              {error}
            </div>
        )}

        {/* Result Display */}
        {repoData && (
            <div className="mt-8 w-full max-w-2xl animate-in fade-in slide-in-from-bottom-4 duration-500">
              <div className="bg-slate-800 border border-slate-700 p-6 rounded-xl flex items-center justify-between shadow-xl">
                <div>
                  <p className="text-sm text-indigo-400 font-medium mb-1 text-left">Found Repository</p>
                  <h2 className="text-2xl font-bold flex items-center gap-2">
                    {repoData.name}
                    {repoData.id}
                    <span className="text-xs bg-slate-700 px-2 py-1 rounded text-slate-400 font-normal">
                </span>
                  </h2>
                </div>
                <a
                    href={url}
                    target="_blank"
                    rel="noreferrer"
                    className="p-3 bg-slate-700 hover:bg-slate-600 rounded-full transition-colors text-white"
                    title="Open on GitHub"
                >
                  <ExternalLink size={20} />
                </a>
              </div>
            </div>
        )}
      </div>
  );
};

export default HomePage;