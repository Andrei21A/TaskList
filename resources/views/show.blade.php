@extends('layouts.app')

@section('title', $task -> title)

@section('content')
    <div class="mb-4">
        <a href="{{ route('tasks.index')
        }}" class="link">← Go back to Home Page </a>
    </div>

    <p class="mb-4 text-slate-700"> {{ $task -> description }}</p>
    @if ($task -> long_description)
        <p class="mb-4 text-slate-700">{{ $task -> long_description }}</p>
    @endif

    <p class="mb-4 text-sm text-slate-500">Created {{ $task -> created_at->diffForHumans() }} ・  Updated {{ $task -> updated_at->diffForHumans() }}</p>
    @if ($task->completed)
        <span class="mb-4 font-medium text-green-500">Completed</span>
    @else
        <span class="mb-4 font-medium text-red-500">Not finished</span>
    @endif
    <div class="flex gap-2">
        <a class="btn" href="{{ route('tasks.edit', ['task' => $task]) }}">Edit</a>
        <form method = "POST" action="{{ route('tasks.toggle-complete', ['task' => $task]) }}">
        @csrf
        @method('PUT')
        <button type="submit" class="btn">
            Mark as {{ $task->completed ? 'not completed' : 'completed' }}
        </button>
        </form>

    <form action="{{ route('tasks.destroy', ['task' => $task->id])}}" method="POST">
        @csrf
        @method('DELETE')
        <button class="btn" type="submit">Delete Task</button>
    </form>
</div>
@endsection
